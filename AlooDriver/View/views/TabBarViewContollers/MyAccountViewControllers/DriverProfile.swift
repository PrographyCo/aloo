//
//  DriverProfile.swift
//  Aloo
//
//  Created by macbook on 10/2/21.
//

import UIKit
import SDWebImage
import SVProgressHUD
class DriverProfile: UIViewController {
    
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var driverImageView: UIImageView!
    
    @IBOutlet weak var driverNameTextField: UITextField!
    @IBOutlet weak var idNumberTextField: UITextField!
    @IBOutlet weak var phoneNumberTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var genderLabel: UILabel!
    @IBOutlet weak var cityLabel: UILabel!
    @IBOutlet weak var genderView: UIView!
    @IBOutlet weak var RegionView: UIView!
    
    private let presnter = ProfilePresnter()
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        // Do any additional setup after loading the view.
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.getProfileData()

    }
    
    private func initlization(){
        presnter.delegate = self
    }
    
    

    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    
}



extension DriverProfile:ProfilePresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)

    }
    
    func getProfileData(data: ProfileCustomData) {
        if let image = data.img{
            driverImageView.sd_setImage(with: URL(string: image))
        }
        driverNameTextField.text = data.name ?? ""
        idNumberTextField.text = "\(data.id ?? -1)"
        emailTextField.text = data.email ?? ""
        phoneNumberTextField.text = data.phone ?? ""
        genderLabel.text = data.gender ?? ""
        cityLabel.text = data.city ?? ""
    }
    
    
}
