# Устаревшее использование типа "УправляемаяФорма" (DeprecatedTypeManagedForm)

| Тип | Поддерживаются<br/>языки | Важность | Включена<br/>по умолчанию | Время на<br/>исправление (мин) | Тэги |
| :-: | :-: | :-: | :-: | :-: | :-: |
| `Дефект кода` | `BSL` | `Информационный` | `Да` | `1` | `standard`<br/>`deprecated` |

<!-- Блоки выше заполняются автоматически, не трогать -->
## Описание диагностики
<!-- Описание диагностики заполняется вручную. Необходимо понятным языком описать смысл и схему работу -->
Начиная с версии платформы 8.3.14 тип "УправляемаяФорма" был переименован, теперь правильно использовать "ФормаКлиентскогоПриложения"

## Источники
<!-- Необходимо указывать ссылки на все источники, из которых почерпнута информация для создания диагностики -->
<!-- Примеры источников

* Источник: [Стандарт: Тексты модулей](https://its.1c.ru/db/v8std#content:456:hdoc)
* Полезная информаця: [Отказ от использования модальных окон](https://its.1c.ru/db/metod8dev#content:5272:hdoc)
* Источник: [Cognitive complexity, ver. 1.4](https://www.sonarsource.com/docs/CognitiveComplexity.pdf) -->
* Источник: [Описание обновлений](https://dl03.1c.ru/content/Platform/8_3_16_1148/1cv8upd_8_3_16_1148.htm)

## Сниппеты

<!-- Блоки ниже заполняются автоматически, не трогать -->
### Экранирование кода

```bsl
// BSLLS:DeprecatedTypeManagedForm-off
// BSLLS:DeprecatedTypeManagedForm-on
```

### Параметр конфигурационного файла

```json
"DeprecatedTypeManagedForm": false
```
