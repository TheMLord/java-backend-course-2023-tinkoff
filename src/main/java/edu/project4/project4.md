# Проект 4. Фрактальное пламя.

> Выполнил Березин Михаил

### Постановка задачи

```
Реализовать алгоритм генерации фрактального пламени.
```

### Пример запуска

```` 
--size 1920x1080 --nonlinear 2 Swirl Spiral --coeff /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/coeff.txt --samples 40000 --iterations 3500 --symmetry 2 --gamma 2.2 --threads 5 --pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/a.bmp --formatImg BMP
````

#### Параметры запуска

````
--size два целых числа для задания итоговому изображению размера разделенные знаком 'x'
````

````
--nonlinear n arg1 arg2... - указывается число передаваемых нелинейных фунций и затем перечисление этих функций
````

##### Доступные нелинейные функции

###### Diamddond

###### Disk

###### Handkerchief

###### Heart

###### Horseshoe

###### Linear

###### Polar

###### Sinusoidal

###### Spherical

###### Spiral

###### Swirl

```
--coeff auto n | pathToCoeffFile - если передается параметр auto то после него должно быть указано количество генерируемых автоматически коэффициентов. Если этого параметра нет, то на вход поступает путь до файла с коэфициентами и цветом
```

##### Пример файла с коэффициентами

```
0.2 -1.26 0.223 0.2422 0 1.23 255 255 255
-.15 0.28 0.2336 0.244 0 0.4455 255 0 0
0.2 0.25 0.223 0.244 0 1.23 0 255 0
-0.15 -1.26 0.2336 0.2422 0 0.4455 255 0 255

формат [a, b, c, d, e, f, red, green, blue]
```

````
--samples n - указывается количество генерируемых точек (целое число)
````

````
--iterations n - указывается количество итераций (целое число)
````

````
--gamma double - указывается параметр гаммы для гамма-коррекции
````

````
--threads n - задается число потоков, которые будут рендерить изображение (целое число)
````

````
—pathToSave path - указывается путь для сохранения изображения, указывается с именем и расширением файла
````

````
--formatImg format - указывается формат итогового изображения
````

##### Доcтупные форматы

###### BMP

###### JPEG

###### PNG

#### Примеры генерируемых изображений

![photo_2023-12-09_15-54-46 (2)](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/d20797f8-e5e6-4eda-9393-14a3d6c92888)

![photo_2023-12-09_15-54-46](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/d019f448-ab4a-4d7a-a456-138dd2e6a6be)

![photo_2023-12-09_15-54-45 (3)](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/ad4b7526-2afd-413e-ba78-a28854577370)

![photo_2023-12-09_15-54-45 (2)](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/db5567f0-0fa7-48ee-b572-e76d2ea89723)
![photo_2023-12-09_15-54-44](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/d3da74d4-7167-4038-8305-d79a6f0888b5)

![photo_2023-12-09_15-54-45](https://github.com/TheMLord/java-backend-course-2023-tinkoff/assets/113773994/7790b15d-2e74-4475-afce-a2c8de2b314c)

