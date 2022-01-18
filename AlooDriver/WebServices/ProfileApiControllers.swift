//
//  ProfileApiControllers.swift
//  Aloo
//
//  Created by macbook on 10/11/21.
//

import Foundation
import ObjectMapper
import Alamofire
import SVProgressHUD
struct ProfileApiControllers{
    
    public static var shard:ProfileApiControllers = {
        let profileApiControllers = ProfileApiControllers()
        return profileApiControllers
    }()
    
    private init(){}
    
    func Profile(callback: @escaping (Bool,ProfileCustomData?,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.profile.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<ProfileCustomData> = Mapper<BaseResponseObject<ProfileCustomData>>().map(JSONString:str){
                    if parsedMapperString.status == true{
                        callback(true, parsedMapperString.data,parsedMapperString.message!)
                    }else{
                        callback(false, nil,parsedMapperString.message!)
                    }
                    
                }
            }
        }
    }
    
    
    func getWalletData(callback: @escaping (Bool,WalletData?,String) ->Void){
        
        SVProgressHUD.show()
        AF.request(API_KEYS.wallet.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<WalletData> = Mapper<BaseResponseObject<WalletData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false, parsedMapperString.data,parsedMapperString.message ?? "")
                }
            }
        }
    }
    
    
    func getMyData(time:String,callback: @escaping (Bool,MyDataData?,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.myData.withId(id: time), method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization": "\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<MyDataData> = Mapper<BaseResponseObject<MyDataData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false, parsedMapperString.data,parsedMapperString.message ?? "")
                }
            }
        }
    }
    
    func firebaseToken(callback: @escaping Callback){
        let parameters: Parameters = ["token":UserDefaults.standard.string(forKey: "fcmToken") ?? ""]
        AF.request(API_KEYS.setFirebaseToken.url, method: .post , parameters:parameters,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Authorization":"\(UserDefaults.standard.value(forKey: "Token") ?? "")","Accept":"application/json"]).validate().responseJSON { (response) in
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<MyDataData> = Mapper<BaseResponseObject<MyDataData>>().map(JSONString:str){
                    if parsedMapperString.status == true{
                        callback(true, parsedMapperString.message!)
                    }else{
                        callback(false, parsedMapperString.message!)
                        
                    }
                    
                }
            }
        }
    }
    
    func getNotification(callback: @escaping (Bool,String,GetNotificationInfo?)->Void){
        let parameters: Parameters = ["token":UserDefaults.standard.string(forKey: "fcmToken") ?? ""]
        AF.request(API_KEYS.getNotification.url, method: .get , parameters:parameters,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Authorization":"\(UserDefaults.standard.value(forKey: "Token") ?? "")","Accept":"application/json"]).validate().responseJSON { (response) in
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<GetNotificationInfo> = Mapper<BaseResponseObject<GetNotificationInfo>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false, parsedMapperString.message ?? "",parsedMapperString.data)
                }
            }
        }
    }
    
    
}
