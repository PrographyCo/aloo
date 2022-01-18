//
//  MyTripsPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 04/11/2021.
//

import UIKit


protocol MyTripsPresnterDelegate {
    func showAlert(title:String,message:String)
    func currentOrdersResponse(data:GetCurrentOrdersData)
}

typealias MyTripsDelegate = MyTripsPresnterDelegate & UIViewController


class MyTripsPresnter:NSObject{
    
    weak var delegate:MyTripsDelegate?
    
    func getCurrentOrders(){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.currentOrders { status, message, data in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.currentOrdersResponse(data: data)
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

