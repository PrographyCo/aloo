//
//  TaxiPeopleViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 02/10/2021.
//

import UIKit
import Cosmos
class TaxiPeopleViewController: UIViewController {
    
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var orderNumberLabel: UILabel!
    @IBOutlet weak var meshwarTypeLabel: UILabel!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    
    @IBOutlet weak var tourDateLabel: UILabel!
    @IBOutlet weak var journeyTimeLabel: UILabel!
    @IBOutlet weak var fromAddressLabel: UILabel!
    @IBOutlet weak var toAddressLabel: UILabel!
    @IBOutlet weak var mealsNumberLabel: UILabel!
    @IBOutlet weak var orderDetailsTableView: UITableView!
    @IBOutlet weak var orderDetailsTableViewHeightConstrant: NSLayoutConstraint!
    var id: String!
    var type:String!
    private var phoneNumber:String?
    private var myOrdersDetails = [Items]()
    private let presnter = TaxiPeoplePresnter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        // Do any additional setup after loading the view.
    }
    
    private func initlization(){
        presnter.delegate = self
        setUpTableView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        meshwarTypeLabel.text = type
        presnter.getOrderData(id: id)
    }
    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func callAction(_ sender: Any) {
        if let phoneNumber = phoneNumber{
            let appURL = URL(string: "tel://\(phoneNumber)")!
            if UIApplication.shared.canOpenURL(appURL) {
                UIApplication.shared.open(appURL)
            }
        }
    }
    
    @IBAction func whatsappCallAction(_ sender: Any) {
        if let phoneNumber = phoneNumber{
            let appURL = URL(string: "https://api.whatsapp.com/send?phone=\(phoneNumber)")!
            if UIApplication.shared.canOpenURL(appURL) {
                UIApplication.shared.open(appURL)
            }
        }
    }
    
    @IBAction func acceptAction(_ sender: Any) {
        presnter.acceptOrder(id: id, distance: "16")
    }
    
}


extension TaxiPeopleViewController:UITableViewDelegate,UITableViewDataSource{
    private func setUpTableView(){
        orderDetailsTableView.delegate = self
        orderDetailsTableView.dataSource = self
        orderDetailsTableView.register(.init(nibName: "OrderDetailsTableViewCell", bundle: nil), forCellReuseIdentifier: "OrderDetailsTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return myOrdersDetails.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "OrderDetailsTableViewCell", for: indexPath) as! OrderDetailsTableViewCell
        cell.setData(data: myOrdersDetails[indexPath.row])
        return cell
    }
    
}


extension TaxiPeopleViewController: TaxiPeoplePresnterDelegate{
    
    func successAcceptOrderAction(response: ConfirmData) {
        let vc = MapTrackerViewController()
        vc.responseData = response
        navigationController?.pushViewController(vc, animated: true)
    }
    
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func dataResponse(data: GetOrderData) {
        orderNumberLabel.text = String(data.id ?? -1)
        userNameLabel.text = data.customer?.name ?? ""
        ratingView.rating = Double(data.customer?.rate ?? 0)
        phoneNumber = data.customer?.phone
        tourDateLabel.text = "\(data.date ?? -1)"
        journeyTimeLabel.text = "Not Supported"
        fromAddressLabel.text = data.place?.name ?? ""
        toAddressLabel.text = data.place?.location_name ?? ""
        myOrdersDetails = data.items ?? []
        orderDetailsTableView.reloadData()
        mealsNumberLabel.text = "\(data.items?.count ?? 0) \(NSLocalizedString("item", comment: ""))"
    }
    
}

