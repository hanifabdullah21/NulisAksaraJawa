package com.singpaulee.sinauaksarajawawithknn.helper

class ListQuiz{

    private var arrayAksaraQuestion = arrayOf(
        "a",
        "n",
        "c",
        "r",
        "k",
        "f",
        "t",
        "s",
        "w",
        "l",
        "p",
        "d",
        "j",
        "y",
        "v",
        "m",
        "g",
        "b",
        "q",
        "z"
    )

    private var arrayQuestion = arrayOf(
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?",
        "Aksara apa iku?"
    )

    private var arrayAnswer = arrayOf(
        "HA", "NA", "CA", "RA", "KA",
        "DA", "TA", "SA", "WA", "LA",
        "PA", "DHA", "JA", "YA", "NYA",
        "MA", "GA", "BA", "THA", "NGA"
    )

    private var choiceAnswer = arrayOf(
        arrayOf("HA", "PA", "LA"),
        arrayOf("KA", "NA", "DA"),
        arrayOf("SA", "DA", "CA"),
        arrayOf("GA", "RA", "MA"),
        arrayOf("KA", "SA", "NA"),

        arrayOf("DA", "SA", "CA"),
        arrayOf("WA", "LA", "TA"),
        arrayOf("SA", "DHA", "THA"),
        arrayOf("THA", "WA", "YA"),
        arrayOf("HA", "NA", "LA"),

        arrayOf("PA", "HA", "RA"),
        arrayOf("DHA", "LA", "MA"),
        arrayOf("JA", "DHA", "TA"),
        arrayOf("JA", "YA", "NGA"),
        arrayOf("YA", "NYA", "PA"),

        arrayOf("MA", "GA", "NYA"),
        arrayOf("GA", "BA", "MA"),
        arrayOf("HA", "BA", "GA"),
        arrayOf("BA", "THA", "CA"),
        arrayOf("THA", "NGA", "NYA")
    )

    fun getAksaraQuestion(x: Int): String{
        return arrayAksaraQuestion[x]
    }

    fun getQuestion(x: Int): String{
        return arrayQuestion[x]
    }

    fun getAnswer(x: Int): String{
        return arrayAnswer[x]
    }

    fun getChoiceAnswer1(x: Int): String{
        return choiceAnswer[x][0]
    }

    fun getChoiceAnswer2(x: Int): String{
        return choiceAnswer[x][1]
    }

    fun getChoiceAnswer3(x: Int): String{
        return choiceAnswer[x][2]
    }
}