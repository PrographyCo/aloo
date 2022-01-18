//
//  MyAccountViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 22/09/2021.
//

import UIKit

class MyAccountViewController: UIViewController {
    
    @IBOutlet weak var accountTableView: UITableView!
    @IBOutlet weak var alertView: UIView!
    @IBOutlet weak var alertTitleLabel: UILabel!
    @IBOutlet weak var alertFirstButton: GraidentButton!
    @IBOutlet weak var alertSecondButton: GraidentButton!
    @IBOutlet weak var shadowView: UIView!
    
    private var isLangView = false
    private var presnter = LogoutPresenter()
    
    
    private var allCellsData = [(image:UIImage,title:String,badge:Int,isLang:Bool,lang:String)]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
    }
    
    private func initlization(){
        presnter.delegate = self
        setUpTabelView()
        setCellsData()
        shadowView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(didSelectShadow)))
    }
    
    
    
    private func setCellsData(){
        allCellsData.append((#imageLiteral(resourceName: "profile-icon"),NSLocalizedString("Personal Profile", comment: ""),0,false,""))
        allCellsData.append((#imageLiteral(resourceName: "notification-icon"),NSLocalizedString("Notification", comment: ""),0,false,""))
        allCellsData.append((#imageLiteral(resourceName: "Group 1-2"),NSLocalizedString("My Data", comment: ""),0,false,""))
        allCellsData.append((#imageLiteral(resourceName: "weg-icon"),NSLocalizedString("Aloo wallet", comment: ""),0,false,""))
        allCellsData.append((#imageLiteral(resourceName: "lang-icon"),NSLocalizedString("Langeuage", comment: ""),0,true,"en"))
        allCellsData.append((#imageLiteral(resourceName: "logout-icon"),NSLocalizedString("Log out", comment: ""),0,false,""))
    }
    
    
    @objc private func didSelectShadow(){
        alertView.isHidden = true
        shadowView.isHidden = true
    }
    
    
    @IBAction func alertFirstButtonAction(_ sender: Any) {
        if isLangView{
            // Ok
            // Convert Language
            let lang = L102Language.currentAppleLanguage() == "ar" ? "en" : "ar"
            L102Language.changeLanguage(view: self, newLang: lang, rootViewController: "MainNav")
            if UserDefaults.standard.string(forKey: "AppLang") == "ar"{
                UserDefaults.standard.setValue("en", forKey: "AppLang")
            }else{
                UserDefaults.standard.setValue("ar", forKey: "AppLang")
            }
        }else{
            // Yes
            // logout
            presnter.logout()
        }
    }
    
    
    @IBAction func alertSecondButtonAction(_ sender: Any) {
        // 'No' or 'I do not want'
        didSelectShadow()
    }
    
    
    
}



extension MyAccountViewController:UITableViewDelegate,UITableViewDataSource{
    private func setUpTabelView(){
        accountTableView.delegate = self
        accountTableView.dataSource = self
        
        accountTableView.register(.init(nibName: "MyAccountPageTableViewCell", bundle: nil), forCellReuseIdentifier: "MyAccountPageTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allCellsData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MyAccountPageTableViewCell", for: indexPath) as! MyAccountPageTableViewCell
        cell.setData(data: allCellsData[indexPath.row])
        cell.delegate = self
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            let vc = storyboard?.instantiateViewController(withIdentifier: "DriverProfile") as! DriverProfile
            navigationController?.pushViewController(vc, animated: true)
            
            break
        case 1:
            let vc = AlertsViewController()
            navigationController?.pushViewController(vc, animated: true)
            
            break
        case 2:
            navigationController?.pushViewController(MyData(), animated: true)
            
            break
        case 3:
            let vc = storyboard?.instantiateViewController(withIdentifier: "AlooWalletViewController") as! AlooWalletViewController
            navigationController?.pushViewController(vc, animated: true)
            
            break
        case 4:
            break
            
        case 5:
            // logout
            showAlert(title: NSLocalizedString("Do you want to log out?", comment: ""), firstButtonText: NSLocalizedString("Yes", comment: ""), secondButtonText: NSLocalizedString("No", comment: ""))
            isLangView = false
            break
        default:
            break
        }
    }
}

extension MyAccountViewController: MyAccountPageCellDelegate{
    func showAlert(title:String,firstButtonText:String,secondButtonText:String){
        alertTitleLabel.text = title
        alertFirstButton.setTitle(firstButtonText, for: .normal)
        alertSecondButton.setTitle(secondButtonText, for: .normal)
        UIView.transition(with: view, duration: 0.3, options: .transitionCrossDissolve,animations: {            self.shadowView.isHidden = false
            self.alertView.isHidden = false
        })
        isLangView = true
    }
    
}

extension MyAccountViewController: MyAccountPresnterDelegate{
    
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func logoutAction() {
        UserDefaults.standard.setValue(nil, forKey: "Token")
        let nav = UINavigationController(rootViewController: LoginDriverViewController())
        nav.modalPresentationStyle = .fullScreen
        navigationController?.present(nav, animated: false, completion: nil)
        
    }
    
    
    
}
