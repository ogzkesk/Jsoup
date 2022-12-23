package com.example.jsoupapp.data.remote

import com.example.jsoupapp.util.Constants.UNSPLASH_URL
import org.jsoup.Jsoup

class DefaultApiImpl : Api {


    override suspend fun searchOnUnsplash(query : String): List<Source> {
        val list = mutableListOf<Source>()

        try {
            val doc = Jsoup
                .connect("https://unsplash.com/s/photos/$query")
                .timeout(10000)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                .get()

            val div = doc.select("div.mItv1")
            val column = div.select("figure")
            val imageSource = column.select("img.tB6UZ.a5VGX")
            val userSource = column.select("a.N2odk.RZQOk.eziW_.cl4O9.KHq0c")

            for(i in 0 until column.size){
                val imgLink = imageSource
                    .eq(i)
                    .attr("src")

                val userProfileLink = userSource
                    .eq(i)
                    .attr("href")

                val userName = userSource
                    .eq(i)
                    .text()

                if(imgLink.startsWith("https://plus")) continue
                if(userName.isEmpty() || userName == "Unsplash+") continue
                if(imgLink.isEmpty()) continue
                if(userProfileLink.isEmpty()) continue

                val profileLink = UNSPLASH_URL+userProfileLink
                println(userProfileLink)
                list.add(Source(userName,profileLink,imgLink))
            }


        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }

        return list
    }

    override suspend fun checkEuro(): Euro? {
        return try {
            val doc = Jsoup.connect("https://bigpara.hurriyet.com.tr/doviz/euro")
                .timeout(10000)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                .get()

            val container = doc.select("div.kurBox")
            val satis = container.select("span.value.up")
            val alis = container.select("span.value.dw")

            val list = mutableListOf<Euro>()

            for(i in 0 until container.size) {
                val al = alis.eq(i).text()
                val sat = satis.eq(i).text()

                list.add(Euro(al,sat))
            }

            println(list[0])
            list[0]
        }catch (e : Exception){
            throw Exception(e.localizedMessage)
        }
    }


}