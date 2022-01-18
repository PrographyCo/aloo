//
//  MapTrackerPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 29/10/2021.
//

import UIKit

protocol MapTrackerPresnterDelegate {
    func showAlert(title:String,message:String)
    func sucessCancel()
    func witingRequestSuccess()
    func toCustomerRequestSuccess(data:ConfirmData)
    func arrivedRequestSuccess()
    func toOrderDeliverSuccess()
    func rateRequsetSuccess()
}


typealias MapTrackerDelegate = MapTrackerPresnterDelegate & UIViewController


class MapTrackerPresnter:NSObject{
    
    
    weak var delegate:MapTrackerDelegate?
    
    
    func cancelOrder(id:String,message:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                OrdersApiControllers.shard.orderCancel(id: id, message: message) { status, message in
                    if status{
                        DispatchQueue.main.async {
                            self.delegate?.sucessCancel()
                        }
                    }else{
                        DispatchQueue.main.async {
                            self.delegate?.showAlertMessage(title:NSLocalizedString("Error", comment: ""), message: message)
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
    
    
    func waitingOrder(id:String){
        GeneralActions.shard.monitorNetwork {
            OrdersApiControllers.shard.orderWaiting(id: id) { status, message in
                if status{
                    DispatchQueue.main.async {
                        self.delegate?.witingRequestSuccess()
                    }
                }else{
                    DispatchQueue.main.async {
                        self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
                    }
                }
            }
        } notConectedAction: {
            DispatchQueue.main.async {
                self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: NSLocalizedString("Internet Connection Error", comment: ""))
            }
        }
    }
    
    
    func orderToCustomer(id:String){
        GeneralActions.shard.monitorNetwork {
            OrdersApiControllers.shard.orderToCustomer(id: id) { status, message , data in
                if status{
                    if let data = data{
                        DispatchQueue.main.async {
                            self.delegate?.toCustomerRequestSuccess(data: data)
                        }
                    }
                }else{
                    DispatchQueue.main.async {
                        self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
                    }
                }
            }
        } notConectedAction: {
            DispatchQueue.main.async {
                self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: NSLocalizedString("Internet Connection Error", comment: ""))
            }
        }
    }
    
    
    func orderDriverArrived(id:String){
        GeneralActions.shard.monitorNetwork {
            OrdersApiControllers.shard.orderDriverArrived(id: id) { status, message in
                if status{
                    DispatchQueue.main.async {
                        self.delegate?.arrivedRequestSuccess()
                    }
                }else{
                    DispatchQueue.main.async {
                        self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
                    }
                }
            }
        } notConectedAction: {
            DispatchQueue.main.async {
                self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: NSLocalizedString("Internet Connection Error", comment: ""))
            }
        }
    }
    
    
    func orderDeliver(id:String){
        GeneralActions.shard.monitorNetwork {
            OrdersApiControllers.shard.orderDeliver(id: id) { status, message in
                if status{
                    DispatchQueue.main.async {
                        self.delegate?.toOrderDeliverSuccess()
                    }
                }else{
                    DispatchQueue.main.async {
                        self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
                    }
                }
            }
        } notConectedAction: {
            DispatchQueue.main.async {
                self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: NSLocalizedString("Internet Connection Error", comment: ""))
            }
        }
    }
    
    
    
    func rateOrder(id:String,rate:Int,msg:String){
        GeneralActions.shard.monitorNetwork {
            OrdersApiControllers.shard.orderRate(id: id, msg: msg, rate: rate) { status, message in
                if status{
                    DispatchQueue.main.async {
                        self.delegate?.rateRequsetSuccess()
                    }
                }else{
                    DispatchQueue.main.async {
                        self.delegate?.showAlert(title: NSLocalizedString("Error", comment: ""), message: message)
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
