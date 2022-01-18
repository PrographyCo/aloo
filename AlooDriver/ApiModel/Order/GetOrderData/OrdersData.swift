



import Foundation
import ObjectMapper

struct OrdersData : Mappable {
    var with : String?
    var without : String?
    var size : String?
    var drinks : [String]?
    var extras : [String]?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        with <- map["with"]
        without <- map["without"]
        size <- map["size"]
        drinks <- map["drinks"]
        extras <- map["extras"]
    }

}
