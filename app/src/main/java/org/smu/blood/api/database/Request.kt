package org.smu.blood.api.database

class Request {
    var requestId: Int? = null
    var userId: String? = null
    var hospitalId: Int? = null
    var bloodType: Int? = null
    var rhType: Boolean? = null
    var donationType: String? = null
    var wardNum: Int? = null
    var patientName: String? = null
    var patientNum: Int? = null
    var protectorContact: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var registerTime: String? = null
    var story: String? = null
    var applicantNum: Int? = null

    constructor(){
    }

    constructor(
        requestId: Int?,
        userId: String?,
        hospitalId: Int?,
        bloodType: Int?,
        rhType: Boolean?,
        donationType: String?,
        wardNum: Int?,
        patientName: String?,
        patientNum: Int?,
        protectorContact: String?,
        startDate: String?,
        endDate: String?,
        registerTime: String?,
        story: String?,
        applicantNum: Int?
    ) {
        this.requestId = requestId
        this.userId = userId
        this.hospitalId = hospitalId
        this.bloodType = bloodType
        this.rhType = rhType
        this.donationType = donationType
        this.wardNum = wardNum
        this.patientName = patientName
        this.patientNum = patientNum
        this.protectorContact = protectorContact
        this.startDate = startDate
        this.endDate = endDate
        this.registerTime = registerTime
        this.story = story
        this.applicantNum = applicantNum
    }

    override fun toString(): String {
        return "Request(requestId=$requestId, userId=$userId, hospitalId=$hospitalId, bloodType=$bloodType, rhType=$rhType, donationType=$donationType, wardNum=$wardNum, patientName=$patientName, patientNum=$patientNum, protectorContact=$protectorContact, startDate=$startDate, endDate=$endDate, registerTime=$registerTime, story=$story, applicantNum=$applicantNum)"
    }
}