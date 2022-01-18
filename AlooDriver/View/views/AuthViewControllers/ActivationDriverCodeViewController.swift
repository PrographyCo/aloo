//
//  ActivationDriverCodeViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 30/09/2021.
//

import UIKit

class ActivationDriverCodeViewController: UIViewController {
    @IBOutlet weak var activationCodeTextField: UITextField!
    @IBOutlet weak var backButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
    }

    
    @IBAction func nextAction(_ sender: Any) {
        UserDefaults.standard.setValue(true, forKey: "isSuccessDriverData")
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func resendAction(_ sender: Any) {
    }
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    
}
