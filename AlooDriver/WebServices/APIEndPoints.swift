//
//  APIEndPoint.swift
//  Aloo
//
//  Created by macbook on 10/11/21.
//
import Foundation

enum API_KEYS: String {

    case BASE_URL = "https://aloo-app.com/api/v1/"
    //Customer
    case USER_LOGIN = "car/auth/login"
    case USER_LOGUT = "car/auth/logout"
    
    case delete = "car/auth/delete"
    
    case setFirebaseToken = "car/token"
    
    //data
    case frequencyAskedQuestion = "data/faq/{id1}?page={id2}"
    
    case getServiceProvider="data/services"
    case getCustomerPrivacy = "data/privacy/driver"
    case customerClientService = "data/clientService/driver"
    case getStatus = "car/getStatus"
    case profile = "car/profile"
    case getOrders = "car/orders/get?lon={id1}&lat={id2}&order_by={id3}&type={id4}"
    case getOrderData = "car/orders/view/{id}"
    case cancelOrder = "car/orders/status/{id}/cancel"
    case orderConfirm = "car/orders/status/{id}/confirm"
    case currentOrder = "car/orders/current"
    case showCurrentOrderDetails = "car/orders/view/current/{id}"
    case OrderWaiting = "car/orders/status/{id}/waiting"
    case orderToCustomer = "car/orders/status/{id}/toCustomer"
    case orderDriverArrived = "car/orders/status/{id}/arrived"
    case orderDeliver = "car/orders/status/{id}/delivered"
    case orderRate = "car/orders/rate/{id}"
    case getAbout = "data/about"
    case wallet = "car/wallet"
    case myData = "car/profile/data?{id}"
    case getNotification = "car/profile/notification"
    
    var url: String {
        switch self {
        case .BASE_URL:
            return API_KEYS.BASE_URL.rawValue
            
        default:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue)"
        }
    }
    
    func withId(id: String) -> String {
        switch self {
        case .getOrderData,.orderRate,.myData,.showCurrentOrderDetails:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}", with: "\(id)"))"
        case .OrderWaiting:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/waiting", with: "\(id)/waiting"))"
        case .orderConfirm:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/confirm", with: "\(id)/confirm"))"
        case .orderDeliver:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/deliver", with: "\(id)/deliver"))"
        case .cancelOrder:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/cancel", with: "\(id)/cancel"))"
        case .orderToCustomer:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/toCustomer", with: "\(id)/toCustomer"))"
        case .orderDriverArrived:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id}/arrived", with: "\(id)/arrived"))"
        default:
            return ""
        }
    }
    
    func with2Id(id1:String,id2:String) -> String{
        switch self {
        case .frequencyAskedQuestion:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "{id1}?page={id2}", with: "\(id1)?page=\(id2)"))"
        default:
            return ""
        }
    }
    
    
    func with4Id(id1:String,id2:String,id3:String,id4:String) -> String{
        switch self {
        case .getOrders:
            return "\(API_KEYS.BASE_URL.rawValue)\(self.rawValue.replacingOccurrences(of: "lon={id1}&lat={id2}&order_by={id3}&type={id4}", with: "lon=\(id1)&lat=\(id2)&order_by=\(id3)&type=\(id4)"))"
        default:
            return ""
        }
    }
    
}
