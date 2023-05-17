package com.example.econstat_android.utils


import com.example.econstat_android.utils.Constant.OPEN_GOOGLE
import com.example.econstat_android.utils.Constant.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {



            //Hello
            message.contains("hello") -> {

                  "hey there !\n 1 : how to add car  \n 2 : how to generate qrcode  \n 3 : how to fill a report \n 4 : Contact Support"


            }
            //Hello
            message.contains("hi") -> {

                "hey there !\n" +
                        " 1 : how to add car  \n" +
                        " 2 : how to generate qrcode  \n" +
                        " 3 : how to fill a report \n" +
                        " 4 : Contact Support"


            }
            //Hello
            message.contains("aslema") -> {

                "hey there !\n" +
                        " 1 : how to add car  \n" +
                        " 2 : how to generate qrcode  \n" +
                        " 3 : how to fill a report \n" +
                        " 4 : Contact Support"


            }
            //Hello
            message.contains("hola") -> {

                "hey there !\n" +
                        " 1 : how to add car  \n" +
                        " 2 : how to generate qrcode  \n" +
                        " 3 : how to fill a report \n" +
                        " 4 : Contact Support"


            }
            //Hello
            message.contains("salut") -> {

                "hey there !\n" +
                        " 1 : how to add car  \n" +
                        " 2 : how to add insurance  \n" +
                        " 3 : how to generate qrcode  \n" +
                        " 4 : how to fill a report \n" +
                        " 5 : Contact Support"


            }

            //1
            message.contains("1") -> {
                "in the car section press on add new car fill the indormation the press add, congratulation youre car is added \uD83D\uDE97"
            }
            message.contains("2") -> {
                "after adding a car press on it , you will see an alerte message in bottom just press it and fill your insurance informations \uD83D\uDCDC"
            }
            //2
            message.contains("3") -> {
                "in the car section you will find a button in the top right with a qr code icon just press it and the other user will scan it to fill your part of the constat"            }
            //3
            message.contains("4") -> {
                "First You have to create a car , then you add an insurance to that car. In accident cases you data will be automaticly imported then you will scan a qr code to fill the other person data, complete the sketch and boom your constat is ready \uD83E\uDDE1"            }

            //4
            message.contains("5") -> {
                 "Contact us on our mail : rayen@dev.tn \uD83D\uDCAC or you can simply call our assistance service : +216 53 344 511 \uD83D\uDCDE"}



            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "say hello first PLEASE"
                    3 -> "We are so happy to visit us , Thank you!  "
                    else -> "error"
                }
            }
        }
    }
}