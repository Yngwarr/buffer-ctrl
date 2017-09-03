# Рублевой рулевой

**Рублевой рулевой** - это приложение, позволяющее эффективно распоряжаться своими денежными средствами.

Приложение предлагает новый подход к планированию бюджета, основанный на идее визуализации, [предложенной Джеймсом Холтом](https://youtu.be/DPFTJayYrnk?t=14m25s). Идея подхода в задании ежедневного лимита затрат и поддержке его с помощью буфера.

Имеется ежемесячный доход пользователя *Д* (заработная плата, стипендия и т. д.) и сумма его постоянных ежемесячных расходов *Р* (ЖКХ, абонентская плата и т. д.). Треть разности *Д — Р* резервируется под буфер, остаток делится на количество дней в текущем месяце. Это ежедневный лимит затрат *П<sub>ежедн</sub> = 2/3 × (Д — Р) / М*, выход за который компенсируется содержимым буфера. Пользователю доступен график, с помощью которого он может определить, является ли перерасход допустимым.

<img src='https://imgur.com/Uwhijvu.png' width='70%'>

Схема графического интерфейса приложения:

> <img src='https://order-of-nop.github.io/web-fencing/MainScheme.svg'>

Диаграммы юзкейсов для каждого окна приложения представлены ниже.

## Main
<img src='https://order-of-nop.github.io/web-fencing/MainActivity.svg'>

## Edit
<img src='https://order-of-nop.github.io/web-fencing/EditActivity.svg'>

## Add
<img src='https://order-of-nop.github.io/web-fencing/AddActivity.svg'>

## List
<img src='https://order-of-nop.github.io/web-fencing/ListActivity.svg'>

