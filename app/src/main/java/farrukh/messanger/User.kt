package farrukh.messanger

data class User (
    var username:String?= null,
    var password: String?=null,
    var fullName: String?=null

) {

    constructor() : this(null,null,null)
}
