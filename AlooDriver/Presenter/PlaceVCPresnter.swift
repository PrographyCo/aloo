//
//  PlaceVCPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 04/11/2021.
//

import UIKit


protocol PlaceVCPresnterDelegate {
    func showAlert(title:String,message:String)
    func showOrderResponse(data:ShowCurrentOrders)
}

typealias PlaceVCDelegate = PlaceVCPresnterDelegate & UIViewController


class PlaceVCPresnter:NSObject{
    
    weak var delegate:PlaceVCDelegate?
    
    
    func showOrderDetails(id:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.showCurrentOrderDetails(id: id) { status, message, data in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.showOrderResponse(data: data)
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
