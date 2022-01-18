//
//  MyData.swift
//  Aloo
//
//  Created by macbook on 10/3/21.
//

import UIKit
import Cosmos

class MyData: UIViewController {
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var driverImageView: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet weak var numberOfTripsLabel: UILabel!
    @IBOutlet weak var numberOfHourLabel: UILabel!
    @IBOutlet weak var totalIncomLabel: UILabel!
    @IBOutlet weak var applicationTransferAmountLabel: UILabel!
    @IBOutlet weak var viewByView: UIViewCustomCornerRadius!
    @IBOutlet weak var viewByTableView: UITableView!
    @IBOutlet weak var shadowView: UIView!
    
    private let presnter = MyDataPresnter()
    private var viewByData = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        
    }
    
    
    private func initlization(){
        presnter.delegate = self
        shadowView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(shadowViewAction)))
        setUpTableView()
        setTimeData()
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.getProfileData(time: "all")
    }
    
    
    @objc private func shadowViewAction(){
        shadowView.isHidden = true
        viewByView.isHidden = true
    }
    
    
    private func setTimeData(){
        viewByData.append(NSLocalizedString("All", comment: ""))
        viewByData.append(NSLocalizedString("Day", comment: ""))
        viewByData.append(NSLocalizedString("Week", comment: ""))
        viewByData.append(NSLocalizedString("Month", comment: ""))
        viewByData.append(NSLocalizedString("Year", comment: ""))
    }
    
    
    @IBAction func seeCustomerReviewAction(_ sender: Any) {
    }
    
    
    @IBAction func backAction(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    
    
    @IBAction func viewByAction(_ sender: Any) {
        UIView.transition(with: view, duration: 0.3, options: .transitionCrossDissolve,animations: {
            self.shadowView.isHidden = false
            self.viewByView.isHidden = false
        })
    }
    
}



extension MyData:UITableViewDelegate,UITableViewDataSource{
    private func setUpTableView(){
        viewByTableView.delegate = self
        viewByTableView.dataSource = self
        
        viewByTableView.register(.init(nibName: "ViewByTableViewCell", bundle: nil), forCellReuseIdentifier: "ViewByTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewByData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ViewByTableViewCell") as! ViewByTableViewCell
        cell.typeLabel.text = viewByData[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath) as! ViewByTableViewCell
        switch cell.typeLabel.text! {
        case NSLocalizedString("All", comment: ""):
            presnter.getProfileData(time: "all")
        case NSLocalizedString("Day", comment: ""):
            presnter.getProfileData(time: "today")
        case NSLocalizedString("Week", comment: ""):
            presnter.getProfileData(time: "week")
        case NSLocalizedString("Month", comment: ""):
            presnter.getProfileData(time: "month")
        case NSLocalizedString("Year", comment: ""):
            presnter.getProfileData(time: "year")
        default:
            break
        }
        viewByView.isHidden = true
        shadowView.isHidden = true
    }
    
}

extension MyData:MyDataPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func setData(data: MyDataData) {
        if let image = data.img{
            driverImageView.sd_setImage(with: URL(string: image))
        }
        nameLabel.text = data.name ?? ""
        ratingView.rating = Double(data.rate ?? 0)
        
        numberOfTripsLabel.text = String(data.rides ?? 0)
        numberOfHourLabel.text = data.hours ?? ""
        totalIncomLabel.text = "\(data.income ?? "") \(NSLocalizedString("SR", comment: ""))"
        applicationTransferAmountLabel.text = "\(data.exchanged ?? 0.0) \(NSLocalizedString("SR", comment: ""))"
    }
    
}
