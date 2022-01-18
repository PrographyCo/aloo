//
//  MyDataPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 28/10/2021.
//

import UIKit


protocol MyDataPresnterDelegate {
    func showAlert(title:String,message:String)
    func setData(data:MyDataData)
}


typealias MyDataDelegate = MyDataPresnterDelegate & UIViewController


class MyDataPresnter:NSObject{
    
    
    weak var delegate:MyDataDelegate?
    
    
    func getProfileData(time:String){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                ProfileApiControllers.shard.getMyData(time: time) { status, data, message in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.setData(data: data)
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
