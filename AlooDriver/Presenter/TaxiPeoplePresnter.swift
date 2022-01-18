//
//  TaxiPeoplePresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 28/10/2021.
//

import UIKit


protocol TaxiPeoplePresnterDelegate {
    func showAlert(title:String,message:String)
    func dataResponse(data:GetOrderData)
    func successAcceptOrderAction(response:ConfirmData)
}

typealias TaxiPeopleDelegate = TaxiPeoplePresnterDelegate & UIViewController


class TaxiPeoplePresnter:NSObject{
    
    weak var delegate:TaxiPeopleDelegate?
    
    func getOrderData(id:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.getOrderData(id: id) { status, message, data in
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
    
    
    func acceptOrder(id:String,distance:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.orderConfirm(id: id,distance: distance) { status, message, data in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.successAcceptOrderAction(response: data)
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
                self.delegate?.showAlert(title: "Error", message: "Internet Connection Error")
            }
        }
    }
    
}




