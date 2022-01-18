//
//  AlooWalletPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 04/11/2021.
//

import UIKit


protocol AlooWalletPresnterDelegate {
    func showAlert(title:String,message:String)
    func walletResponse(data:WalletData)
}

typealias AlooWalletDelegate = AlooWalletPresnterDelegate & UIViewController


class AlooWalletPresnter:NSObject{
    
    weak var delegate:AlooWalletDelegate?
    
    func getWalletData(){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                ProfileApiControllers.shard.getWalletData { status, data, message in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.walletResponse(data: data)
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
