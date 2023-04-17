# Эндпоинты API

## GET-теры

### getScene
* Получить характеристику кругов на сцене

#### Входные данные
* Отсутствуют

#### Выходные данные
* ArrayList<String> : круги, расположенные в сцене
* * CoordX CoordY CoordZ Radius

### getCurrentStatus
* Получить текущий статус занятости сервера

#### Входные данные
* Отсутствуют

#### Выходные данные
* ArrayList<String> : статус успеха начала выполнения
* * ArrayList<String>[0] = true : успешное начало
* * ArrayList<String>[0] = false : ошибка начала выполнения

#### getResults
* Получить результаты выполнения алгоритма

#### Входные данные
* Отсутствуют

#### Выходные данные
* ArrayList<String> : статус успеха начала выполнения, результаты выполнения
* * ArrayList<String>[0] = true : сервер занят
* * ArrayList<String>[0] = false : сервер свободен
* * ArrayList<String>[1] : время выполнения

## SET-теры

### refreshScene
* Повторно создать сцену

#### Входные данные
* numOfCircles : int : Количество кругов
* numOfDots : int : Количество точек

#### Выходные данные
* ArrayList<String> : статус успеха начала выполнения
* * ArrayList<String>[0] = true : успешное начало
* * ArrayList<String>[0] = false : ошибка начала выполнения

### startLinearMC
* Запуск однопоточного алгоритма Монте-Карло

#### Входные данные
* Отсутствуют

#### Выходные данные
* ArrayList<String> : статус успеха начала выполнения
* * ArrayList<String>[0] = true : успешное начало
* * ArrayList<String>[0] = false : ошибка начала выполнения
