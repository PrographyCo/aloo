//
//  ProfilePresenter.swift
//  AlooDriver
//
//  Created by macbook on 10/24/21.

import Foundation

import UIKit

protocol ProfilePresnterDelegate {
    func showAlert(title:String,message:String)
    func getProfileData(data:ProfileCustomData)
}



typealias ProfileDelegate = ProfilePresnterDelegate & UIViewController

class ProfilePresnter:NSObject{
    weak var delegate:ProfileDelegate?
    
    func getProfileData(){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                ProfileApiControllers.shard.Profile { status ,data, message in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.getProfileData(data: data)
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

