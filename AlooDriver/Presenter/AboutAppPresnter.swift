//
//  AboutAppPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 04/11/2021.
//

import UIKit


protocol AboutAppPresnterDelegate {
    func showAlert(title:String,message:String)
    func aboutDataResonse(data:AboutData)
}

typealias AboutAppDelegate = AboutAppPresnterDelegate & UIViewController

class AboutAppPresnter:NSObject{
    
    weak var delegate:AboutAppDelegate?
    
    
    func getAboutData(){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                DataApiControllers.shard.GetAbout { status, message, data in
                    if status{
                        if let data = data{
                            DispatchQueue.main.async {
                                self.delegate?.aboutDataResonse(data: data)
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
