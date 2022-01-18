//
//  HomeDriverViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 29/09/2021.
//

import UIKit
import CoreLocation
class HomeDriverViewController: UIViewController {
    
    @IBOutlet weak var selectView: UIView!
    @IBOutlet weak var homeDriverTableView: UITableView!
    @IBOutlet weak var avalibleButton: UIButton!
    @IBOutlet weak var serviceTypeView: UIView!
    @IBOutlet weak var repSupermarketStackView: UIStackView!
    @IBOutlet weak var repPharmacyStackView: UIStackView!
    @IBOutlet weak var repRestaurantStackView: UIStackView!
    @IBOutlet weak var closeStackView: UIStackView!
    @IBOutlet weak var farStackView: UIStackView!
    @IBOutlet weak var shadowView: UIView!
    @IBOutlet weak var selectActionView: UIViewCustomCornerRadius!

    @IBOutlet var sortByButtons: [UIButton]!
    @IBOutlet var serviceTypeButtons: [UIButton]!
    @IBOutlet weak var serviceTypeActionView: UIViewCustomCornerRadius!
    
    private var myOrders = [GetOrderItem]()
    private let presnter = HomePresnter()
    private var locationManager = CLLocationManager()
    private var currentLocation:CLLocationCoordinate2D?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
    }
    
    
    private func initlization(){
        setStackViewsActions()
        setStatus()
        presnter.delegate = self
        setUpLocationRequest()
        navigationController?.navigationBar.isHidden = true
        checkingLogin()
        setUpTableView()
        setUpViews()
    }
    
    private func setStackViewsActions(){
        repSupermarketStackView.isUserInteractionEnabled = true
        repPharmacyStackView.isUserInteractionEnabled = true
        repRestaurantStackView.isUserInteractionEnabled = true
        closeStackView.isUserInteractionEnabled = true
        farStackView.isUserInteractionEnabled = true
        
        repSupermarketStackView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(repSupermarketStackViewAction)))
        repPharmacyStackView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(repPharmacyStackViewAction)))
        repRestaurantStackView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(repRestaurantStackViewAction)))
        closeStackView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(closeStackViewAction)))
        farStackView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(farStackViewAction)))
        
    }
    
    private func setStatus(){
        homeDriverTableView.isHidden = false
        avalibleButton.setTitle(NSLocalizedString("Available", comment: ""), for: .normal)
        avalibleButton.setTitleColor(#colorLiteral(red: 1, green: 1, blue: 1, alpha: 1), for: .normal)
        avalibleButton.backgroundColor = #colorLiteral(red: 0.5647058824, green: 0.1098039216, blue: 0.1137254902, alpha: 1)
    }
    
    
    private func setUpLocationRequest(){
        if CLLocationManager.locationServicesEnabled(){
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestAlwaysAuthorization()
            locationManager.startUpdatingLocation()
        }
    }
    
    
    private func checkingLogin(){
        if UserDefaults.standard.string(forKey: "Token") == nil{
            let nav = UINavigationController(rootViewController: LoginDriverViewController())
            nav.modalPresentationStyle = .fullScreen
            navigationController?.present(nav, animated: false, completion: nil)
        }
    }
    
    
    private func setUpViews(){
        shadowView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(shadowAction)))
        selectView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(selectViewAction)))
        serviceTypeView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(serviceTypeViewAction)))
    }
  
    
    private func checkServiceTypeRadioButton(index:Int,buttons:[UIButton]){
        for bIndex in 0..<buttons.count{
            if bIndex == index{
                buttons[bIndex].isSelected = true
                continue
            }
            buttons[bIndex].isSelected = false
        }
    }
    
    
    @objc private func closeStackViewAction(){
        if let currentLocation = currentLocation{
            presnter.getAllOrders(lon: "\(currentLocation.longitude)" , lat: "\(currentLocation.latitude)", order_by: "close",type: "")
            checkServiceTypeRadioButton(index: 0,buttons: sortByButtons)
            selectActionView.isHidden = true
            shadowView.isHidden = true
        }
    }
    
    @objc private func farStackViewAction(){
        if let currentLocation = currentLocation{
            presnter.getAllOrders(lon: "\(currentLocation.longitude)" , lat: "\(currentLocation.latitude)", order_by: "far",type: "")
            checkServiceTypeRadioButton(index: 1,buttons: sortByButtons)
            selectActionView.isHidden = true
            shadowView.isHidden = true
        }
    }
    
    
    
    @objc private func repSupermarketStackViewAction(){
        if let currentLocation = currentLocation{
            presnter.getAllOrders(lon: "\(currentLocation.longitude)" , lat: "\(currentLocation.latitude)", order_by: "",type: "1")
            checkServiceTypeRadioButton(index: 0,buttons: serviceTypeButtons)
            serviceTypeActionView.isHidden = true
            shadowView.isHidden = true
        }
    }
    
    @objc private func repPharmacyStackViewAction(){
        if let currentLocation = currentLocation{
            presnter.getAllOrders(lon: "\(currentLocation.longitude)" , lat: "\(currentLocation.latitude)", order_by: "",type: "2")
            checkServiceTypeRadioButton(index: 1,buttons: serviceTypeButtons)
            serviceTypeActionView.isHidden = true
            shadowView.isHidden = true
        }
    }
    
    @objc private func repRestaurantStackViewAction(){
        if let currentLocation = currentLocation{
            presnter.getAllOrders(lon: "\(currentLocation.longitude)" , lat: "\(currentLocation.latitude)", order_by: "",type: "3")
            checkServiceTypeRadioButton(index: 2,buttons: serviceTypeButtons)
            serviceTypeActionView.isHidden = true
            shadowView.isHidden = true
        }
    }
    
    
    
    
    
    @objc private func selectViewAction(){
        self.selectActionView.isHidden = false
        self.shadowView.isHidden = false
    }
    
    @objc private func serviceTypeViewAction(){
        self.serviceTypeActionView.isHidden = false
        self.shadowView.isHidden = false
    }
    
    
    @objc private func shadowAction(){
        selectActionView.isHidden = true
        serviceTypeActionView.isHidden = true
        shadowView.isHidden = true
    }
    
    
    @IBAction func availableAction(_ sender: Any) {
        if avalibleButton.titleLabel!.text! == NSLocalizedString("Unavailable", comment: ""){
            homeDriverTableView.isHidden = false
            avalibleButton.setTitle(NSLocalizedString("Available", comment: ""), for: .normal)
            avalibleButton.setTitleColor(#colorLiteral(red: 1, green: 1, blue: 1, alpha: 1), for: .normal)
            avalibleButton.backgroundColor = #colorLiteral(red: 0.5647058824, green: 0.1098039216, blue: 0.1137254902, alpha: 1)
        }else{
            homeDriverTableView.isHidden = true
            avalibleButton.setTitle(NSLocalizedString("Unavailable", comment: ""), for: .normal)
            avalibleButton.setTitleColor(#colorLiteral(red: 0.5647058824, green: 0.1098039216, blue: 0.1137254902, alpha: 1), for: .normal)
            avalibleButton.backgroundColor = #colorLiteral(red: 0.9254901961, green: 0.9098039216, blue: 0.9019607843, alpha: 1)
        }
    }
    
    
    
    
}

extension HomeDriverViewController:UITableViewDelegate,UITableViewDataSource{
    private func setUpTableView(){
        homeDriverTableView.delegate = self
        homeDriverTableView.dataSource = self
        homeDriverTableView.register(.init(nibName: "HomeDriverTableViewCell", bundle: nil), forCellReuseIdentifier: "HomeDriverTableViewCell")
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return myOrders.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "HomeDriverTableViewCell", for: indexPath) as! HomeDriverTableViewCell
        cell.setData(data: myOrders[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let vc  = storyboard?.instantiateViewController(withIdentifier: "TaxiPeopleViewController") as! TaxiPeopleViewController
        vc.type = "\(myOrders[indexPath.row].vendor_type?.name ?? "") \(NSLocalizedString("Representative", comment: ""))"
        vc.id = String(myOrders[indexPath.row].id ?? -1)
        navigationController?.pushViewController(vc, animated: true)
    }
    
}


extension HomeDriverViewController:CLLocationManagerDelegate{
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if currentLocation == nil {
            if let location  = locations.first{
                currentLocation = location.coordinate
                if let long = currentLocation?.longitude, let lat = currentLocation?.latitude{
                    presnter.getAllOrders(lon: "\(long)" , lat: "\(lat)", order_by: "",type: "")
                }
            }
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        // Error
    }
    
}


extension HomeDriverViewController: HomePresnterDelegate{
    func dataResponse(data: GetOrder) {
        myOrders = data.items ?? []
        homeDriverTableView.reloadData()
    }
    
    
    func showAlert(title:String,message:String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
}
