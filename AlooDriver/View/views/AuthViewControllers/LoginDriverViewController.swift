//
//  LoginDriverViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 29/09/2021.
//

import UIKit

class LoginDriverViewController: UIViewController {
    
    
    @IBOutlet weak var pathImageView: UIImageView!
    @IBOutlet weak var phoneNumberTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    private var presnter = LoginPresnter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
    }
    
    
    private func initlization(){
        navigationController?.navigationBar.isHidden = true
        presnter.delegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            pathImageView.transform = .init(rotationAngle: .pi)
        }
    }

    
    @IBAction func forgetPasswordAction(_ sender: Any) {
    }
    
    
    @IBAction func loginAction(_ sender: Any) {
        presnter.loginAction(phone: phoneNumberTextField.text!, password: passwordTextField.text!)
    }
    
    @IBAction func signUpAction(_ sender: Any) {
        let vc = WebViewViewController()
        vc.link = "https://aloo-app.com/driver-register"
        let nav = UINavigationController(rootViewController: vc)
        nav.modalPresentationStyle = .fullScreen
        self.navigationController?.present(nav, animated: true, completion: nil)
    }
    
}


extension LoginDriverViewController:LoginPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func successLoginAction() {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let nav = storyboard.instantiateViewController(withIdentifier: "MainNav") as! UINavigationController
        nav.modalPresentationStyle = .fullScreen
        navigationController?.present(nav, animated: true, completion: nil)

    }
    
}


