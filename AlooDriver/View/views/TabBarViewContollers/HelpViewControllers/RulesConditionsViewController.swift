//
//  RulesConditionsViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 29/09/2021.
//

import UIKit

class RulesConditionsViewController: UIViewController {

    @IBOutlet weak var termsTextView: UITextView!
    
    @IBOutlet weak var backButton: UIButton!
    private let presnter = RulesConditionsPresnter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presnter.delegate = self
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.getPrivceyText()
    }

    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    

}

extension RulesConditionsViewController:RulesConditionsPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func privacyResponse(data: PrivacyPolicyData) {
        termsTextView.text = data.data ?? ""
    }

}
