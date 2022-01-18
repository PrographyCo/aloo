//
//  AlooWalletViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 28/09/2021.
//

import UIKit

class AlooWalletViewController: UIViewController {
    
    @IBOutlet weak var balanceLabel: UILabel!
    @IBOutlet weak var walletTableView: UITableView!
    @IBOutlet weak var backButton: UIButton!
    
    
    private let presnter = AlooWalletPresnter()
    private var data = [Wallet_details]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        
    }
    
    private func initlization(){
        presnter.delegate = self
        setTableView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.getWalletData()
    }
    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
}

extension AlooWalletViewController:UITableViewDelegate,UITableViewDataSource{
    private func setTableView(){
        walletTableView.delegate = self
        walletTableView.dataSource = self
        
        walletTableView.register(.init(nibName: "WalletTableViewCell", bundle: nil), forCellReuseIdentifier: "WalletTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "WalletTableViewCell", for: indexPath) as! WalletTableViewCell
        cell.setData(data: data[indexPath.row])
        return cell
    }
    
    
}

extension AlooWalletViewController:AlooWalletPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func walletResponse(data: WalletData) {
        balanceLabel.text = data.wallet?.amount ?? ""
        self.data = data.wallet_details ?? []
        walletTableView.reloadData()
        
    }
    
}
