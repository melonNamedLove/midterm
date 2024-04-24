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
    private val bulletin = mutableListOf<bbDTO>()              //기존 변수 tests -> bulletin으로 수정
    private val userList = mutableListOf<userData>()
    private val loginUserList = mutableListOf<loginListData>()

    @GetMapping("/read")            //all
    fun getAllbbDto(): ResponseEntity<List<bbDTO>> {        //기존 매개변수 제거
        val response = bulletin
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/readone")            //one               //1건 조회
    fun getOnebbDto(
         titleNum: bbDtoReadable
//    ){
    ): ResponseEntity<bbDTO> {
//        log.info("!!!!!!!!!!!!!!!!!!!"+titleNum.num)
        val bulletinList = bulletin
        val none = bbDTO(
            num = 0,
            title = "NOT FOUND",
            detail = "",
            writer = "",
            time = ""
        )
        var found = -1
        for ( i:Int in 0 until  bulletinList.size){
            if(bulletinList[i].num.toInt() == titleNum.num.toInt()){
                found = i
                break
            }
        }
        if (found < 0){
            return ResponseEntity.ok().body(none)
        }

        return ResponseEntity.ok().body(bulletinList[found])
    }


    @PostMapping("/create")
    fun postWrite(
        @RequestBody bbDTORe: bbDtoRequest //bbDtoRequest로는 4개를 받아올 수 있음
    ) : ResponseEntity<bbDTO> {
        val bbDTOwrite = bbDTO( // 5개의 요소 삽입
            num = bbDTORe.num,
            title = bbDTORe.title,
            detail = bbDTORe.detail,
            writer = bbDTORe.writer,
            time = LocalDateTime.now().toString() //자동으로 지금 시간을 time에  집어넣음
        )

        bulletin.add(bbDTOwrite) //tests라는 mutableListOf<bbDTO> 에 집어넣음
        return ResponseEntity.ok().body(bbDTOwrite)

    }

    //update
    @PostMapping("/update")
    fun updateDTO( updates: bbDtoRequest) : ResponseEntity<bbDTO> {

        val bulletinList = bulletin
        val none = bbDTO(
            num = 0,
            title = "NOT FOUND",
            detail = "",
            writer = "",
            time = ""
        )
        val updated = bbDTO(
            num = updates.num,
            title = updates.title,
            detail = updates.detail,
            writer = updates.writer,
            time = LocalDateTime.now().toString()       //수정시간
        )


        var found = -1
        for ( i:Int in 0 until  bulletinList.size){
            if(bulletinList[i].num.toInt() == updates.num.toInt()){
                found = i
                break
            }
        }
        if (found < 0){
            return ResponseEntity.ok().body(none)
        }
        bulletin[found] = updated

        return ResponseEntity.ok().body(bulletin[found])
    }





    //delete
    @PostMapping("/delete")
    fun deleteDTO(
        titleNum: bbDtoReadable,
    ): ResponseEntity<bbDTO> {
        val bulletinList = bulletin


        val rmd = bbDTO(
            num = 0,
            title = "NOT FOUND",
            detail = "",
            writer = "",
            time = ""
        )
        val none = bbDTO(
            num = 0,
            title = "NOT FOUND",
            detail = "",
            writer = "",
            time = ""
        )



        var found = -1
        for ( i:Int in 0 until  bulletinList.size){
            if(bulletinList[i].num.toInt() == titleNum.num.toInt()){
                found = i
                break
            }
        }

        if (found < 0){
            return ResponseEntity.ok().body(none)
        }

        bulletinList.removeAt(found)
        return ResponseEntity.ok().body(rmd)
    }
    //

    //유저 생성
    @PostMapping("/addUser")
    fun addUser(
        inputUserData: addUserData
    ) : ResponseEntity<String> {
        val addData = userData(
            userNum = inputUserData.userNum,
            userName = inputUserData.userName,
            userId= inputUserData.userId,
            userPw= inputUserData.userPw,
        )

        userList.add(addData)
        val response = "sign up succeed"
        return ResponseEntity.ok().body(response)

    }

    //전체 유저 목록 - 상의 후 결정
//    @GetMapping("/userList")            //all
//    fun getAlluser(): ResponseEntity<List<userData>> {
//        val response = userList
//        return ResponseEntity.ok().body(response)
//    }

    //회원확인
    @GetMapping("/userExistence")
    fun getUser(
        inputLoginData: loginData
    ): ResponseEntity<String> {        //기존 매개변수 제거

        var response=""
        var found = -1
        for ( i:Int in 0 until  userList.size){
            if(userList[i].userId.equals(inputLoginData.userId)){
                found = i
                break
            }
        }
        if(found < 0){
            response = "id-not-found"
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }else{
            if (userList[found].userPw.equals(inputLoginData.userPw)){

                response = "valid-${userList[found].userId}-welcome"

                val valid=loginListData (
                        userValid = response
                        )
                loginUserList.add(valid)
            }else{
                response = response+ "pw-no-match"
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
            }
        }



        return ResponseEntity.ok().body(response)
    }


    //로그인
    @PostMapping("/login")
    fun login(
        inputString: String
    ) : ResponseEntity<String> {
        var response = ""
        for (i:Int in 0 until loginUserList.size){
            if(inputString.equals(loginUserList[i])){
                val loginUserId = inputString.split("-")[1]
                for(j:Int in 0 until userList.size){
                    if(loginUserId.equals(userList[j].userId)){
                        userList[j].login ="loginyeah"
                        break
                    }
                }
                break
            }else{
                response = "error-login"
            }

        }
        response = "login succeed you can write now"
        return ResponseEntity.ok().body(response)

    }
}