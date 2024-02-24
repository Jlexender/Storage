## Реализация

Будем стараться реализовывать паттерн MVC.

Стек технологий: 
* JDK 17
  * ``com.google.code.gson:gson:2.10.1``
  * ``org.projectlombok:lombok:1.18.30``
* OS Linux (Ubuntu)

### Архитектура

Перечислю этапы построения архитектуры:

Этап 1: набросок

![](architectures/architecture.svg)

Этап 2: не связанная файловая система

![](architectures/architecture2_bad.svg)

Этап 3: текущий вариант

![](architectures/architecture2_MVC.svg)
