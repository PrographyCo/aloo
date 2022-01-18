//
//  GeneralActions.swift
//  Aloo
//
//  Created by Hany Alkahlout on 12/10/2021.
//

import UIKit
import FirebaseMessaging
import GoogleMaps
import Network

class GeneralActions: NSObject {
    
    public static var shard: GeneralActions = {
        let generalActions = GeneralActions()
        return generalActions
    }()
    
    
    func showAlert(viewController:UIViewController,title:String,message:String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(.init(title: NSLocalizedString("Ok", comment: ""), style: .cancel, handler: nil))
        viewController.present(alert, animated: true, completion: nil)
    }
    
    func monitorNetwork(conectedAction:(()->Void)?,notConectedAction:(()->Void)?){
        let monitor = NWPathMonitor()
        monitor.pathUpdateHandler = { path in
            if path.status != .satisfied{
                notConectedAction?()
            }else{
                conectedAction?()
            }
        }
        let queue = DispatchQueue(label: "Network")
        monitor.start(queue: queue)
    }
    
    func getAddressFromCoordinate(lat:String?,lng:String?,callBack: @escaping (_ status:Bool,_ location:String) -> Void){
        if let lat = lat , let lng = lng{
            GMSGeocoder().reverseGeocodeCoordinate(CLLocationCoordinate2D(latitude: Double(lat)!, longitude: Double(lng)!)) { (response, error) in
                if error != nil{
                    callBack(false,"")
                    return
                }
                if let response = response {
                    let result = response.firstResult()?.thoroughfare ?? "location not determined"
                    callBack(true,result)
                }
            }
        }
    }
    
    
    
}



