package com.happynicetime.reviewliferepeat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.text.method.ScrollingMovementMethod
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.lang.StringBuilder
import java.util.Arrays

class PrintReviewsActivity : AppCompatActivity() {
    private lateinit var textViewFileName: TextView
    private lateinit var textViewFileText: TextView
    private lateinit var buttonPrintStarting: Button
    private lateinit var buttonPrintEnding: Button
    private lateinit var buttonPreviousFile: Button
    private lateinit var buttonNextFile: Button
    private var printStarting = -1
    private var printEnding = -1
    private lateinit var files: Array<File>
    private var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_reviews)
        var dir: File = File(applicationContext.getFilesDir(), "ReviewLifeRepeat/reviews/")
        if(!dir.exists()){
            val toast = Toast.makeText(this, "No Reviews To Show", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
        files = dir.listFiles()
        if (files.isEmpty()) {
            val toast = Toast.makeText(this, "No Reviews To Show", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
        Arrays.sort(files)
        files = files.reversedArray()
        //for(item in files){
        //    println(item.toString())//works
        //}
        textViewFileName = findViewById(R.id.textViewFileNameForPrint)
        var file = files[0]
        textViewFileName.setText(file.toString())
        var fileString = readFileOnInternalStorage(file)
        textViewFileText = findViewById(R.id.textViewFileTextForPrint)
        textViewFileText.setText(fileString)
        textViewFileText.movementMethod = ScrollingMovementMethod()
        buttonPreviousFile = findViewById(R.id.buttonPreviousFileForPrint)
        var currentFileIndex = 0;
        buttonPreviousFile.setOnClickListener {
            currentFileIndex++
            if (currentFileIndex < files.size) {
                file = files[currentFileIndex]
                fileString = readFileOnInternalStorage(file)
                textViewFileText.setText(fileString)
                textViewFileName.setText(file.toString())
            } else {
                currentFileIndex--;
            }
        }
        buttonNextFile = findViewById(R.id.buttonNextFileForPrint)
        buttonNextFile.setOnClickListener {
            currentFileIndex--;
            if (currentFileIndex >= 0) {
                file = files[currentFileIndex]
                fileString = readFileOnInternalStorage(file)
                textViewFileText.setText(fileString)
                textViewFileName.setText(file.toString())
            } else {
                currentFileIndex++;
            }
        }
        buttonPrintStarting = findViewById(R.id.buttonPrintStarting)
        buttonPrintStarting.setOnClickListener {
            printStarting = currentFileIndex
            printIfCan()
        }
        buttonPrintEnding = findViewById(R.id.buttonPrintEnding)
        buttonPrintEnding.setOnClickListener {
            printEnding = currentFileIndex
            printIfCan()
        }
    }

    private fun printIfCan() {
        if(printStarting == -1 || printEnding == -1){
            return
        }
        var printMe: StringBuilder = StringBuilder("<html><body>")
        if(printStarting < printEnding){//print from recent to old , low index to high index
            for(i in printStarting .. printEnding){
                printMe.append("<b>")
                printMe.append(files[i].absoluteFile.name)
                printMe.append("</b>")
                printMe.append("<br>")
                printMe.append(readFileOnInternalStorage(files[i]).replace(System.lineSeparator(),"<br>"))
                printMe.append("<br>")
            }
        }else{//print old to recent, high index to low index
            for(i in printStarting downTo printEnding){
                printMe.append("<b>")
                printMe.append(files[i].absoluteFile.name)
                printMe.append("</b>")
                printMe.append("<br>")
                printMe.append(readFileOnInternalStorage(files[i]).replace(System.lineSeparator(),"<br>"))
                printMe.append("<br>")
            }
        }
        printMe.append("</body></html>")
        //println(printMe)
        val webView = WebView(this.applicationContext)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = false

            override fun onPageFinished(view: WebView, url: String) {
                //Log.i(TAG, "page finished loading $url")
                createWebPrintJob(view)
                mWebView = null
            }
        }
        webView.loadDataWithBaseURL(null, printMe.toString(), "text/HTML", "UTF-8", null)
        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView
        
        printStarting = -1
        printEnding = -1
    }

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        (this.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"

            // Get a print adapter instance
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            // Create a print job with name and adapter instance
            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            )//.also { printJob ->

                // Save the job object for later status checking
                //printJobs += printJob
            //}
        }
    }
}