Процедура Тест()
    Если БезопасныйРежим() ИЛИ Тест = Истина Тогда  // Срабатывание
         // Логика выполнения в безопасном режиме...
    ИначеЕсли Не БезопасныйРежим() Тогда // Срабатывание
        // Логика выполнения в небезопасном режиме...
    КонецЕсли;

    Если Не БезопасныйРежим() Тогда // Срабатывание
         // Логика выполнения в небезопасном режиме...
    КонецЕсли;

    Если БезопасныйРежим() <> Ложь Тогда // Нет срабатывания
        // Логика выполнения в безопасном режиме...
    КонецЕсли;

    Если Тест() ИЛИ Тест = Истина Тогда  // Нет срабатывания
        // код
    КонецЕсли;
КонецПроцедуры