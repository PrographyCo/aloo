//
//  MapTrackerViewController.swift
//  Aloo
//
//  Created by Hany Alkahlout on 02/10/2021.
//

import UIKit
import GoogleMaps
import Cosmos
class MapTrackerViewController: UIViewController {
    
    @IBOutlet weak var headerView: UIView!
    @IBOutlet weak var shadowView: UIView!
    @IBOutlet weak var driverRateView: UIViewCustomCornerRadius!
    @IBOutlet weak var driverName: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet weak var submittedRateView: UIViewCustomCornerRadius!
    @IBOutlet weak var didNotArriveView: UIView!
    @IBOutlet weak var clockImage: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var timeRemindingLabel: UILabel!
    @IBOutlet weak var mapView: UIView!
    @IBOutlet weak var messageAlertView: UIView!
    @IBOutlet weak var messageLabel: UITextView!
    @IBOutlet weak var startNavigatingButton: GraidentButton!
    
    public var responseData:ConfirmData!
    private var presnter = MapTrackerPresnter()
    
    private var locationManager = CLLocationManager()
    private var currentLocation:CLLocationCoordinate2D?
    let userMarker = GMSMarker()
    private let googleMap = GMSMapView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        didNotArriveView.isHidden = false
        driverRateView.isHidden = true
        nameLabel.text = responseData.car?.name ?? ""
    }
    
    
    
    private func initlization(){
        shadowView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(shadowAction)))
        presnter.delegate = self
        setUpLocationRequest()
    }
    
    
    private func setUpLocationRequest(){
        if CLLocationManager.locationServicesEnabled(){
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestAlwaysAuthorization()
            locationManager.startUpdatingLocation()
        }
    }
    
    @objc private func shadowAction(){
        messageAlertView.isHidden = true
        shadowView.isHidden = true
    }
    
    @IBAction func cancelOrderAction(_ sender: Any) {
        shadowView.isHidden = false
        messageAlertView.isHidden = false
    }
    
    
    @IBAction func callAction(_ sender: Any) {
        let appURL = URL(string: "tel://\(String(describing: responseData.car?.phone ?? ""))")!
        if UIApplication.shared.canOpenURL(appURL) {
            UIApplication.shared.open(appURL)
        }
    }
    
    
    @IBAction func whatsappAction(_ sender: Any) {
        let appURL = URL(string: "https://api.whatsapp.com/send?phone=\(String(describing: responseData.car?.phone ?? ""))")!
        if UIApplication.shared.canOpenURL(appURL) {
            UIApplication.shared.open(appURL)
        }
    }
    
    
    @IBAction func startNavigatingAction(_ sender: UIButton) {
        
        switch startNavigatingButton.titleLabel!.text! {
        case NSLocalizedString("Waiting", comment: ""):
            clockImage.isHidden = false
            presnter.waitingOrder(id: String(responseData.id ?? -1))
        case NSLocalizedString("Move to Customer", comment: ""):
            presnter.orderToCustomer(id: String(responseData.id ?? -1))
        case NSLocalizedString("Order Driver Arrived", comment: ""):
            presnter.orderDriverArrived(id: String(responseData.id ?? -1))
        case NSLocalizedString("Order Deliver", comment: ""):
            presnter.orderDeliver(id: String(responseData.id ?? -1))
        case NSLocalizedString("Submit Evaluation", comment: ""):
            presnter.rateOrder(id: String(responseData.id ?? -1), rate: 5, msg: messageLabel.text!)
        case NSLocalizedString("Ok", comment: ""):
            submittedRateView.isHidden = true
            navigationController?.popViewController(animated: true)
        default:
            break
        }
    }
    
    @IBAction func messageCancelAction(_ sender: Any) {
        presnter.cancelOrder(id: "\(responseData.id ?? -1)", message: messageLabel.text!)
    }
    
}




extension MapTrackerViewController:CLLocationManagerDelegate{
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if currentLocation == nil{
            if let location  = locations.first{
                currentLocation = location.coordinate
                let addresslocation = CLLocationCoordinate2D(latitude: Double(responseData.place?.lat ?? "0.0")!, longitude: Double(responseData.place?.lon ?? "0.0")!)
                showGoogleMap(withCoordinate: currentLocation!,addresslocation: addresslocation)
            }
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        // Error
    }
    
    private func showGoogleMap(withCoordinate coordinate :CLLocationCoordinate2D,addresslocation:CLLocationCoordinate2D) {
        
        let bounds = GMSCoordinateBounds(coordinate: coordinate, coordinate: addresslocation)
        
        let update = GMSCameraUpdate.fit(bounds, withPadding: 50.0)
        googleMap.moveCamera(update)
        
        googleMap.frame = mapView.bounds
        mapView.addSubview(googleMap)
        
        userMarker.position = coordinate
        userMarker.iconView = UIImageView(image: #imageLiteral(resourceName: "current-user-location"))
        userMarker.map = googleMap
        
        let addressMarker = GMSMarker()
        addressMarker.position = addresslocation
        addressMarker.iconView = UIImageView(image: #imageLiteral(resourceName: "current-user-location"))
        addressMarker.map = googleMap
        
        
    }
    
}

extension MapTrackerViewController:MapTrackerPresnterDelegate{
    func witingRequestSuccess() {
        startNavigatingButton.setTitle(NSLocalizedString("Move to Customer", comment: ""), for: .normal)
    }
    
    func toCustomerRequestSuccess(data:ConfirmData) {
        startNavigatingButton.setTitle(NSLocalizedString("Order Driver Arrived", comment: ""), for: .normal)
        showGoogleMap(withCoordinate: currentLocation!,addresslocation: CLLocationCoordinate2D(latitude: Double(data.place?.lat ?? "0.0") ?? 0.0, longitude: Double(data.place?.lon ?? "0.0") ?? 0.0))
    }
    
    func arrivedRequestSuccess() {
        startNavigatingButton.setTitle(NSLocalizedString("Order Deliver", comment: ""), for: .normal)
    }
    
    func toOrderDeliverSuccess() {
        driverRateView.isHidden = false
        startNavigatingButton.setTitle(NSLocalizedString("Submit Evaluation", comment: ""), for: .normal)
    }
    
    func rateRequsetSuccess() {
        driverRateView.isHidden = true
        submittedRateView.isHidden = false
        startNavigatingButton.setTitle(NSLocalizedString("Ok", comment: ""), for: .normal)
    }
    
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func sucessCancel() {
        navigationController?.popToRootViewController(animated: true)
    }
}



