//
//  FAQPresnter.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 04/11/2021.
//

import UIKit


protocol FAQPresnterDelegate {
    func showAlert(title:String,message:String)
    func dataResponse(data:[FrequentlyQueItems])
}

typealias FAQDelegate = FAQPresnterDelegate & UIViewController


class FAQPresnter: NSObject{
    
    weak var delegate:FAQDelegate?
    
    func getFAQuestions(page:Int){
        GeneralActions.shard.monitorNetwork {
            DispatchQueue.main.async {
                DataApiControllers.shard.frequentlyQue(page: String(page)) { status, data, message in
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

