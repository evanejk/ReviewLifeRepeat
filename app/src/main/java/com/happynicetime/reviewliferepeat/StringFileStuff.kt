package com.happynicetime.reviewliferepeat

import android.content.Context
import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun writeFileOnInternalStorage(mcoContext: Context, sFileName: String, sBody: String): String{
    var dir: File = File(mcoContext.getFilesDir(), "ReviewLifeRepeat")
    if(!dir.exists()){
        dir.mkdir();
    }
    try {
        var gpxFile: File = File(dir, sFileName)
        var gpxFileDir: File = gpxFile.parentFile
        if(!gpxFileDir.exists()){
            gpxFileDir.mkdir()
        }
        if(!gpxFile.exists()){
            gpxFile.createNewFile()
        }

        //println("saving file: $gpxFile");

        var writer: FileWriter = FileWriter(gpxFile)
        writer.append(sBody)
        writer.flush()
        writer.close()
        return gpxFile.toString()
    }catch(e: Exception){
        e.printStackTrace()
        return ""
    }
}

fun writeFileOnInternalStorage(gpxFile: File, sBody: String): String{
    try {
        var gpxFileDir: File = gpxFile.parentFile
        if(!gpxFileDir.exists()){
            gpxFileDir.mkdir()
        }
        if(!gpxFile.exists()){
            gpxFile.createNewFile()
        }

        //println("saving file: $gpxFile");

        var writer: FileWriter = FileWriter(gpxFile)
        writer.append(sBody)
        writer.flush()
        writer.close()
        return gpxFile.toString()
    }catch(e: Exception){
        e.printStackTrace()
        return ""
    }
}

fun readFileOnInternalStorage(mcoContext: Context, sFileName: String): String{
    var dir: File = File(mcoContext.getFilesDir(), "ReviewLifeRepeat")
    try{
        var gpxFile: File = File(dir,sFileName)
        if(gpxFile.exists()) {
            var reader: FileReader = FileReader(gpxFile)
            val stringBuilder = StringBuilder()
            var readByte: Int
            while (reader.read().also { readByte = it } != -1) {
                stringBuilder.append(readByte.toChar())
            }
            return stringBuilder.toString()
        }else{
            return "";
        }
    }catch(e: Exception){
        e.printStackTrace()
        return ""
    }
}

fun readFileOnInternalStorage(gpxFile: File): String{
    try{
        if(gpxFile.exists()) {
            var reader: FileReader = FileReader(gpxFile)
            val stringBuilder = StringBuilder()
            var readByte: Int
            while (reader.read().also { readByte = it } != -1) {
                stringBuilder.append(readByte.toChar())
            }
            return stringBuilder.toString()
        }else{
            return "";
        }
    }catch(e: Exception){
        e.printStackTrace()
        return ""
    }
}