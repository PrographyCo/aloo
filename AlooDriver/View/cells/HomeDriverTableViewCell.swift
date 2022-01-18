//
//  HomeDriverTableViewCell.swift
//  Aloo
//
//  Created by Hany Alkahlout on 30/09/2021.
//

import UIKit

class HomeDriverTableViewCell: UITableViewCell {

    @IBOutlet weak var pathLineImageView: UIImageView!
    @IBOutlet weak var deliveryTitleLabel: UILabel!
    @IBOutlet weak var driverNameLabel: UILabel!
    
    @IBOutlet weak var fromAddressLabel: UILabel!
    @IBOutlet weak var toAddressLabel: UILabel!
    @IBOutlet weak var cournerImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        if L102Language.currentAppleLanguage() == "ar"{
            pathLineImageView.transform = .init(rotationAngle: .pi)
        }
    }
    
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
    func setData(data:GetOrderItem){
        let type = data.vendor_type?.name ?? ""
        changeImage(type: type)
        deliveryTitleLabel.text = "\(type) \(NSLocalizedString("Representative", comment: ""))"
        driverNameLabel.text = data.customer_name ?? ""
        fromAddressLabel.text = data.place?.name ?? ""
        toAddressLabel.text = data.place?.location_name ?? ""
    }
    
    
    private func changeImage(type:String){
        switch type {
        case "supermarkets":
            cournerImageView.image = #imageLiteral(resourceName: "cart-icon")
            break
        case "restaurants":
            cournerImageView.image = #imageLiteral(resourceName: "unselected-res-icon")
            break
        case "pharmacies":
            cournerImageView.image = #imageLiteral(resourceName: "pp-icon")
            break
        default:
            break
        }
    }
    
}

