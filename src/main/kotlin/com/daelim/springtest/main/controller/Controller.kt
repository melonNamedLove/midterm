package com.daelim.springtest.main.controller

import com.daelim.springtest.main.api.model.dto.*
import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import net.datafaker.Faker
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import org.slf4j.LoggerFactory      //logger factory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

@RestController
class Controller {
    private val log = LoggerFactory.getLogger(this.javaClass)!!     //log 사용위해 선언
    //    private val bulletin = mutableListOf<bbDTO>()              //기존 변수 tests -> bulletin으로 수정
    private val userList = mutableListOf<userData>()

    @GetMapping("/user/login")            //login
    fun loginUser(
        @RequestBody user: userLoginData
    ): ResponseEntity<userData> {
        val userInput = userLoginData(
            email = user.email,
            password = user.password
        )
        var found = -1
        for ( i:Int in 0 until  userList.size){  //검색부
            if(userList[i].email.equals(userInput.email)){
                if(userList[i].password.equals(userInput.password)){
                    found = i
                    break
                }
            }
        }
        var returnData = userData(
            fullName = userList[found].fullName,
            email = userList[found].email,
            password = userList[found].password
        )

        if (found < 0){
////            return HttpStatusCode.valueOf(404)
//            return ResponseEntity.notFound().build()
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userData())
        }else{

            return ResponseEntity.ok().body(userList[found])
        }


    }

    @PostMapping("/user/create")    //회원가입 부
    fun createUser(
        @RequestBody user: userData
    ) : ResponseEntity<userData> {
        val userInput = userData(
            fullName = user.fullName,
            email = user.email,
            password = user.password
        )

        userList.add(userInput)
        return ResponseEntity.ok().body(user)

    }

    var lottoList = mutableListOf<List<Int>>()

    @GetMapping("/lotto")            //lotto
    fun lottolotto(
    ): ResponseEntity<lottonum> {
        var nr1 = mutableListOf(7)
        var nr2 = mutableListOf(7)
        var nr3 = mutableListOf(7)
        var nr4 = mutableListOf(7)
        var nr5 = mutableListOf(7)
        val returnLotto = lottonum(
            num1 = nr1,
            num2 = nr2,
            num3 = nr3,
            num4 = nr4,
            num5 = nr5,

            )

        for ( i:Int in 0 until 5){

            var numbersRow = mutableListOf<Int>(7)
            for (i in 0 until 7) {
                var temp = Random().nextInt(45)
                if (!numbersRow.contains(temp)) {
                    numbersRow.add(temp)
                } else {
                    temp = Random().nextInt(45)
                }

            }
        }

        return ResponseEntity.ok().body(returnLotto)

    }
    //lotto post
}