# Классы объектов

## Circle
* Круг

## Конструктор
* Circle(coordX, coordY, radius)

### Переменные и константы
* coordX{get} : double : Координата центра окружности по оси X
* coordY{get} : double : Координата центра окружности по оси Y
* radius{get} : double : Текущий радиус окружности

## Circle
* Точка

## Конструктор
* Dot(coordX, coordY)

### Переменные и константы
* coordX{get} : double : Координата точки по оси X
* coordY{get} : double : Координата точки по оси Y

## Scene
* Сцена

### Функции
* refreshScene(int) : void : точка входа для генерации новой сцены
* refreshSceneWorker : Runnable : процесс генерации новой сцены
* getScene : ArrayList<String> : получение всех характеристик кругов в виде списка строк

### Переменные и константы
* circles{get, set} : ArrayList<Circle> : множество окружностей
* maxRadius{get} : double : Наибольший радиус окружностей
* minRadius{get} : double : Наименьший радиус окружностей
* maxCoordX{get} : double : Наибольшая положительная координата X
* minCoordX{get} : double : Наибольшая отрицательная координата X
* maxCoordY{get} : double : Наибольшая положительная координата Y
* minCoordY{get} : double : Наибольшая отрицательная координата Y