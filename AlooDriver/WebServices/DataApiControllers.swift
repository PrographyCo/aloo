//
//  DataApiControllers.swift
//  Aloo
//
//  Created by macbook on 10/11/21.
//

import Foundation
import ObjectMapper
import Alamofire
import SVProgressHUD
struct DataApiControllers{
    
    public static var shard:DataApiControllers = {
        let dataApiControllers = DataApiControllers()
        return dataApiControllers
    }()
    
    private init(){}
    
    func privacyPolicy(callback: @escaping (Bool,String,PrivacyPolicyData?) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.getCustomerPrivacy.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json","Authorization":"\(UserDefaults.standard.string(forKey: "Token") ?? "")"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<PrivacyPolicyData> = Mapper<BaseResponseObject<PrivacyPolicyData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false,parsedMapperString.message ?? "" ,parsedMapperString.data)
                }
            }
        }
    }
    
    func GetAbout(callback: @escaping (Bool,String,AboutData?)->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.getAbout.url, method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<AboutData> = Mapper<BaseResponseObject<AboutData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false,parsedMapperString.message ?? "",parsedMapperString.data)
                }
            }
        }
        
    }
    
    
    func customerClientService(type:String,question:String,callback: @escaping Callback){
        let parameters: Parameters = ["type":type, "question":question]
        SVProgressHUD.show()
        AF.request(API_KEYS.customerClientService.url, method: .post , parameters:parameters,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<AboutData> = Mapper<BaseResponseObject<AboutData>>().map(JSONString:str){
                    callback(parsedMapperString.status ?? false, parsedMapperString.message ?? "")
                }
            }
        }
    }
    
    
    func frequentlyQue(page:String,callback: @escaping (Bool,[FrequentlyQueItems]?,String) ->Void){
        SVProgressHUD.show()
        AF.request(API_KEYS.frequencyAskedQuestion.with2Id(id1: "driver", id2: page), method: .get , parameters:nil,headers:["Accept-Language": UserDefaults.standard.string(forKey: "AppLang") ?? UserDefaults.standard.string(forKey: "AppLang") ?? "en","Accept":"application/json"]).validate().responseJSON { (response) in
            SVProgressHUD.dismiss()
            if let data  = response.data,let str : String = String(data: data, encoding: .utf8){
                if let parsedMapperString : BaseResponseObject<FrequentlyQueData> = Mapper<BaseResponseObject<FrequentlyQueData>>().map(JSONString:str){
                    callback(true, parsedMapperString.data?.items,parsedMapperString.message ?? "")
                }
            }
        }
    }
    
    
}







