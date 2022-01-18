//
//  OrdersApiControllers.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 24/10/2021.
//

import UIKit
import Alamofire
import ObjectMapper
import SVProgressHUD

class OrdersApiControllers{
    
    public static var shard:OrdersApiControllers = {
        let ordersApiControllers = OrdersApiControllers()
        return ordersApiControllers
    }()
    
    private init(){}
    
    func getOrders(lon:String,lat:String,order_by:String,type:String,callback: @escaping (Bool,String,GetOrder?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.getOrders.with4Id(id1: lon, id2: lat, id3: order_by,id4: type), method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
    }
    
    
    func getOrderData(id:String,callback: @escaping (Bool,String,GetOrderData?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.getOrderData.withId(id: id), method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrderData> = Mapper<BaseResponseObject<GetOrderData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
        
    }
    
    
    func orderCancel(id:String,message:String,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.cancelOrder.withId(id: id), method: .post , parameters:["message":message],headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }
    
    func orderConfirm(id:String,distance:String,callback: @escaping (Bool,String,ConfirmData?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.orderConfirm.withId(id: id), method: .post , parameters:["distance":distance],headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<ConfirmData> = Mapper<BaseResponseObject<ConfirmData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
    }
    func orderWaiting(id:String,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.OrderWaiting.withId(id: id), method: .post,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<CustomerMoving> = Mapper<BaseResponseObject<CustomerMoving>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }
    
    
    func currentOrders(callback: @escaping (Bool,String,GetCurrentOrdersData?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.currentOrder.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetCurrentOrdersData> = Mapper<BaseResponseObject<GetCurrentOrdersData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
    }
    
    
    func showCurrentOrderDetails(id:String,callback: @escaping (Bool,String,ShowCurrentOrders?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.showCurrentOrderDetails.withId(id: id), method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<ShowCurrentOrders> = Mapper<BaseResponseObject<ShowCurrentOrders>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
    }
    
    
    func orderDriveArrived(id:String,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.orderDriverArrived.withId(id: id), method: .post,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }
    
    
    func orderDeliver(id:String,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.orderDeliver.withId(id: id), method: .post,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }
    
    func orderRate(id:String,msg:String,rate:Int,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        let parameters: Parameters = ["message":msg,
                                      "rate":rate]
        AF.request(API_KEYS.orderRate.withId(id: id), method: .post,parameters:parameters,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }
    
    
    func orderToCustomer(id:String,callback: @escaping (Bool,String,ConfirmData?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.orderToCustomer.withId(id: id), method: .post,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<ConfirmData> = Mapper<BaseResponseObject<ConfirmData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error",parsedMapperString.data)
                }
            }
        }
    }
    
    func orderDriverArrived(id:String,callback: @escaping (Bool,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.orderDriverArrived.withId(id: id), method: .post,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<GetOrder> = Mapper<BaseResponseObject<GetOrder>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false ,parsedMapperString.message ?? "Server Error")
                }
            }
        }
    }

}


