# Система "Брокер"

## Умови
- кожен користувач системи повинен бути зареєстрований і мати особистий рахунок.
- перш ніж почати користуватися системою користувач має авторизуватись
- користувач з роллю "адміністратор" має інструменти для перегляду чинних ордерів, виконаних угод, і
  керувати стартом\зупинкою торговельної сесії
- нових користувачів реєструє адміністратор

## Опис
Кожен зареєстрований користувач має змогу подати ордер на
придбання\продаж довільної кількості одиниць деяких інструментів за
ціною не вище\нижче, ніж встановив користувач.
Це може бути що завгодно - цінні папери, валюта, нафтопродукти,
агропродукція і тому подібне. Цей список зафіксовано окремим довідником, 
його редагування через функції системи не передбачено, але він є доступним для перегляду.
Всі операції виконуються з моменту старту торговельної сесії до моменту її припинення
(кнопки у користувача “адміністратор”). Для кожного ордера (купівля\продаж) система
намагається знайти 1 (або більше) ордер іншої сторони (продаж\купівля) які задовольняють
обмеженням один одного. Якщо такі ордери знайдено - між ними створюється угода, факт якої
буде відображено на сторінках авторів цих ордерів.

## Правила
1. Кожен ордер повинен бути виконаний повністю (кількість одиниць інструментів) або не виконаний зовсім.
2. Кожен ордер виконується лише один раз - сума одиниць інструментів за угодами ордера дорівнює
кількості одиниць в ордері.
3. Ордер може бути виконаний більше ніж однією угодою, але (див. п. 1).
4. Всі ордери, які не вдалось виконати на момент завершення сесії скасовуються.

В налаштуваннях ордера передбачено опціональне поле активності
до визначеного терміну. Якщо до цього терміну ордер не виконаний,
він повинен бути скасований не чекаючи завершення торговельної сесії.
The following was discovered as part of building this project: