package encryptdecrypt
import java.io.File


fun main(args: Array<String>) {
    val abc = mutableListOf('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')
    //val cba = abc.asReversed()
    //needs error handling


    var code = "enc"
    var s = ""
    var key = 0
    var inn = ""
    var out = ""
    var output = ""
    var type = ""

    for(it in args.indices step 2){
        if(args[it] == "-data") {
            s = args[it + 1]
        }else if(args[it] == "-mode") {
            code = args[it + 1]
        }else if(args[it] == "-key") {
            key = args[it + 1].toInt()
        }else if(args[it] == "-in") {
            inn = args[it + 1]
        }else if(args[it] == "-out") {
            out = args[it + 1]
        }else if(args[it] == "-alg") {
            type = args[it + 1]
        }
    }

    if(s == "" && inn != ""){
        s = File(inn).readText()
    }
    println(s)
    var ind = 0
    var upper = false
    var found = true
    fun encrypt(){
        if(type == "unicode") {
            for (a in s) {
                output += a + key
            }
        }else if(type == "shift"){
            str@for(a in s){
                upper = if(a.isUpperCase()) true else false
                for(i in abc.indices){
                    found = false
                    if(a.lowercase() == abc[i].toString()) {
                        ind = i + key
                        if (ind > 26) ind -= 26
                        if(upper) {
                            output += abc[ind].uppercase()
                        } else output += abc[ind].toString()
                        found = true
                        continue@str
                    }
                }
                if(!found) output += a
            }
        }

    }
    fun decrypt(){
        if(type == "unicode") {
            for (a in s) {
                output += a - key
            }
        }else if(type == "shift"){
            str@for(a in s){
                upper = if(a.isUpperCase()) true else false
                for(i in abc.indices){
                    found = false
                    if(a.lowercase() == abc[i].toString()){
                        ind = i - key
                        if (ind < 0) ind += 26
                        if(upper) {
                            output += abc[ind].uppercase()
                        } else output+= abc[ind].toString()
                        continue@str
                    }
                }
                if(!found) output += a
            }
        }
    }
    if(code == "enc") encrypt()
    else if(code == "dec") decrypt()
    println("$output*")
    if(out == "") {
        println(output)
    }else File(out).writeText(output)
}