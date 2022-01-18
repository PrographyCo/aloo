//
//  CustomerMoving.swift
//  AlooDriver
//
//  Created by macbook on 11/1/21.
//

import Foundation
import ObjectMapper

struct CustomerMoving : Mappable {
    
    var id : Int?
    var lat : String?
    var lon : String?
    
    init?(map: Map) {
        
    }
    
    mutating func mapping(map: Map) {
        
        id <- map["id"]
        lat <- map["lat"]
        lon <- map["lon"]
        
    }
}
