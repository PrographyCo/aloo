//
//  AuthApiControllers.swift
//  Aloo
//
//  Created by macbook on 10/11/21.
//
import Foundation
import ObjectMapper
import Alamofire
import SVProgressHUD

typealias Callback = ( _ status: Bool ,_ message:String) -> Void
typealias CallbackCheck = ( _ status: Bool) -> Void


struct AuthApiControllers{
    
    public static var shard:AuthApiControllers = {
        let authApiControllers = AuthApiControllers()
        return authApiControllers
    }()
    
    private init(){}
    
    
    func logout( callback: @escaping Callback){
        let token = UserDefaults.standard.value(forKey: "Token") ?? ""
        
        SVProgressHUD.show()
        AF.request(API_KEYS.USER_LOGUT.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Authorization": "\(token)","Accept":"application/json"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<LoginData> = Mapper<BaseResponseObject<LoginData>>().map(JSONString:str){
                    if parsedMapperString.status == true{
                        callback(true, parsedMapperString.message!)
                    }else{
                        callback(false, parsedMapperString.message!)
                    }
                }
            }
        }
    }
    
    func Login(phone: String,password:String, callback: @escaping (LoginData?,Bool,String) ->Void){
        let parameters: Parameters = ["login_number":phone,
                                      "password":password,
                                      "type" : "ios"
        ]
        SVProgressHUD.show()
        AF.request(API_KEYS.USER_LOGIN.url, method: .post , parameters:parameters,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                
                if let parsedMapperString : BaseResponseObject<LoginData> = Mapper<BaseResponseObject<LoginData>>().map(JSONString:str){
                    if parsedMapperString.status == true{
                        callback(parsedMapperString.data!, true, parsedMapperString.message!)
                    }else{
                        callback( nil , false, parsedMapperString.message!)
                        
                    }
                    
                }
            }
        }
    }
    
}

