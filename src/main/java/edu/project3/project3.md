# Проект 3. Анализатор логов.

> Выполнил Березин Михаил

### Постановка задачи
```
На вход программе передается путь к одному или нескольким NGINX лог-файлам 
в виде локального шаблона или URL.

Также передается путь, куда сохранять результат анализа логов. И способ
записи статистики по логам - в виде MD или ADOC.

Опционально передается from, to - временные промежутки выборки логов.

Требуется обработать переданные файлы с логами и вывести статистику по ним.
```

### Реализованная статистика
* Метрика с общей информацией (просмотренные файлы, дата начала, дата конца, общее количество запросов, средний размер ответа)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/fb3c0647-7a9d-47de-b673-c1ad158c7720)
* Метрика с запрашиваемыми ресурсами (ресурс и количество запросов)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/76458382-6ec0-4e98-82bd-dbdc534b567c)
* Метрика с кодами ответов (код, его расшифровка, количество)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/fd43c17a-fc49-4fab-bbab-7ebf423d1537)
* Метрика активности пользователей (ip адрес, количество запросов от него)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/6f969e7a-0acb-48fe-8354-fdd97d2326c7)
* Метрика дневной активности (дата, количество запросов в эту дату)
  ![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/4a4d7619-4adf-4902-b915-279b1f18c80c)


### Пример запуска
```
 java -jar nginx-log-stats.jar --pathToLog https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs --pathToSave result.md [--from 2023-08-31] [--to 2023-09-30] --format md
```
#### Парметры запуска
* --pathToLog - обязательный параметр - путь до файла или файлов с логами. Может быть задан в виде URL адреса, при этом он ссылается только на 1 ресурс или через glob шаблон для локальных файлов
* --pathToSave - обязательный параметр - путь сохранения результата работы программы. Должен быть указан путь до конкретного файла, не обязательно существующего, также требуется указывать расширение .md если вы хотите получить ответ в виде MarkDown или .adoc если хотите получить в виде ASKIIDOC.
* --format - обязательный парамер - формат сохранения. 2 типа сохранения результата - markdown и adoc. Если параметр будет указан не корректно, то по-умолчанию вернется adoc
* --from, --to - необязательные параметры. Дата для фильтрации логов. Дата передается в формате ISO8601

### Пример вывода результата .md
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/90ccc6de-3e89-4d19-94ea-67caee4485df)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/5c816d1a-0497-4b1d-ae2b-779833a97696)

### Пример вывода результата .adoc
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/96f2d3dd-02a4-4a16-8f55-78bed7f12488)
![image](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/6b2a1a74-9407-4038-ad0d-d17d122a6f01)
