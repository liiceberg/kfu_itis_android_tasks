package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.comparator

import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel

class CardRatingComparator : Comparator<CardModel> {
    override fun compare(o1: CardModel, o2: CardModel): Int {
        return o1.rating.compareTo(o2.rating)
    }
}