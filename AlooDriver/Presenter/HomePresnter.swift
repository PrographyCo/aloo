//
//  HomePresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 25/10/2021.
//

import UIKit

protocol HomePresnterDelegate {
    func showAlert(title:String,message:String)
    func dataResponse(data:GetOrder)
}

typealias HomeDelegate = HomePresnterDelegate & UIViewController

class HomePresnter:NSObject{
    
    weak var delegate:HomeDelegate?
    
    
    func getAllOrders(lon:String,lat:String,order_by:String,type:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.getOrders(lon: lon, lat: lat, order_by: order_by,type:type) { status, message, data in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.dataResponse(data: data)
                            }
                        }
                    }else{
                        DispatchQueue.main.async {
                            self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
                        }
                    }
                }
            }
        } notConectedAction: {
            DispatchQueue.main.async {
                self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: NSLocalizedString("Internet Connection Error", comment: ""))
            }
        }
    }
}


