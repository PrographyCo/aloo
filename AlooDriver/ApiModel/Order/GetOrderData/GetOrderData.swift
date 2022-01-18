



import Foundation
import ObjectMapper

struct GetOrderData : Mappable {
    var id : Int?
    var status : String?
    var total_price : Double?
    var date : Int?
    var customer : Customer?
    var items : [Items]?
    var place : Place?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        id <- map["id"]
        status <- map["status"]
        total_price <- map["total_price"]
        date <- map["date"]
        customer <- map["customer"]
        items <- map["items"]
        place <- map["place"]
    }

}


