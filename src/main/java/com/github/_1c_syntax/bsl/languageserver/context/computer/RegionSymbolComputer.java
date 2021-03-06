/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2020
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Gryzlov <nixel2007@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package com.github._1c_syntax.bsl.languageserver.context.computer;

import com.github._1c_syntax.bsl.languageserver.context.DocumentContext;
import com.github._1c_syntax.bsl.languageserver.context.symbol.RegionSymbol;
import com.github._1c_syntax.bsl.languageserver.utils.Ranges;
import com.github._1c_syntax.bsl.languageserver.utils.Trees;
import com.github._1c_syntax.bsl.parser.BSLParser;
import com.github._1c_syntax.bsl.parser.BSLParserBaseVisitor;
import com.github._1c_syntax.bsl.parser.BSLParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.lsp4j.Range;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public final class RegionSymbolComputer
  extends BSLParserBaseVisitor<ParseTree>
  implements Computer<List<RegionSymbol>> {

  private final DocumentContext documentContext;
  private final Deque<Pair<RegionSymbol.RegionSymbolBuilder, BSLParser.RegionStartContext>> regionStack = new ArrayDeque<>();
  private final List<RegionSymbol> regions = new ArrayList<>();
  private final List<BSLParserRuleContext> allNodes = new ArrayList<>();

  public RegionSymbolComputer(DocumentContext documentContext) {
    this.documentContext = documentContext;
  }

  @Override
  public List<RegionSymbol> compute() {
    regionStack.clear();
    regions.clear();
    allNodes.clear();

    Trees.getDescendants(documentContext.getAst()).stream()
      .filter(node -> node instanceof BSLParserRuleContext)
      .map(node -> (BSLParserRuleContext) node)
      .filter(node -> (node.getStop() != null)
        && (node.getStart() != null))
      .collect(Collectors.toCollection(() -> allNodes));

    visitFile(documentContext.getAst());

    regionStack.clear();
    allNodes.clear();

    return new ArrayList<>(regions);
  }

  @Override
  public ParseTree visitRegionStart(BSLParser.RegionStartContext ctx) {

    RegionSymbol.RegionSymbolBuilder builder = RegionSymbol.builder()
      .name(ctx.regionName().getText())
      .regionNameRange(Ranges.create(ctx.regionName()))
      .startRange(Ranges.create(ctx));

    regionStack.push(Pair.of(builder, ctx));
    return super.visitRegionStart(ctx);
  }

  @Override
  public ParseTree visitRegionEnd(BSLParser.RegionEndContext ctx) {

    if (regionStack.isEmpty()) {
      return super.visitRegionEnd(ctx);
    }

    var pair = regionStack.pop();

    RegionSymbol.RegionSymbolBuilder builder = pair.getLeft();
    BSLParser.RegionStartContext regionStartContext = pair.getRight();

    Range range = Ranges.create(regionStartContext, ctx);
    builder
      .range(range)
      .endRange(Ranges.create(ctx))
    ;

    // zero-based ranges
    int regionStartLine = range.getStart().getLine() + 1;
    int regionEndLine = range.getEnd().getLine() + 1;
    List<BSLParserRuleContext> regionNodes = allNodes.stream()
      .filter(node ->
        node.getStart().getLine() > regionStartLine
          && node.getStart().getLine() < regionEndLine)
      .collect(Collectors.toList());

    builder.nodes(regionNodes);

    RegionSymbol region = builder.build();

    regions.add(region);

    return super.visitRegionEnd(ctx);
  }

}
