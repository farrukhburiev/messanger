package farrukh.messanger

data class Message (
    var from:String?= null,
    var to: String?=null,
    var body: String?=null,
    var date:String?=null

) {

    constructor() : this(null,null,null,null)
}
