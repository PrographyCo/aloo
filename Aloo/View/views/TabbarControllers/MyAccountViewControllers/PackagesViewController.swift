//
//  PackagesViewController.swift
//  Aloo
//
//  Created by Dev. Hany Alkahlout on 04/12/2021.
//

import UIKit

class PackagesViewController: UIViewController {
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var packagesTableView: UITableView!
    
    private let presnter = PackagePresnter()
    private var data = [Packages]()
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        // Do any additional setup after loading the view.
    }
    
    private func initlization(){
        setupTableView()
        presnter.delegate = self
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.getPackages()
    }
    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    
}

extension PackagesViewController:UITableViewDelegate,UITableViewDataSource{
    private func setupTableView(){
        packagesTableView.delegate = self
        packagesTableView.dataSource = self
        
        packagesTableView.register(.init(nibName: "PackgesTableViewCell", bundle: nil), forCellReuseIdentifier: "PackgesTableViewCell")
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PackgesTableViewCell") as! PackgesTableViewCell
        cell.delegate = self
        cell.setData(data: data[indexPath.row])
        return cell
    }
    
}

extension PackagesViewController:PackagePresnterDelegate,PackgesTableViewCellDelegate{
    func showCustomeAlertWithUrl(title: String, message: String, url: String) {
        let alertVC = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alertVC.addAction(.init(title: "Ok", style: .cancel, handler: { action in
            let vc = WebViewViewController()
            vc.link = url
            let nav = UINavigationController(rootViewController: vc)
            nav.modalPresentationStyle = .fullScreen
            self.navigationController?.present(nav, animated: true, completion: nil)
        }))
        present(alertVC, animated: true, completion: nil)
    }
    
    func showCustomAlert(title: String, message: String) {
        let alertVC = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alertVC.addAction(.init(title: "Ok", style: .cancel, handler: { action in
            self.navigationController?.popViewController(animated: true)
        }))
        present(alertVC, animated: true, completion: nil)
    }
    
    func setPackages(data: PackageData) {
        self.data = data.packages ?? []
        packagesTableView.reloadData()
    }
    
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    
}
