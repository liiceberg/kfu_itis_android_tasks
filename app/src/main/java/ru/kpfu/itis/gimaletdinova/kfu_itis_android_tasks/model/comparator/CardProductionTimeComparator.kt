package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.comparator

import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel

class CardProductionTimeComparator : Comparator<CardModel> {
    override fun compare(o1: CardModel, o2: CardModel): Int {
        return o1.productionTime - o2.productionTime
    }
}