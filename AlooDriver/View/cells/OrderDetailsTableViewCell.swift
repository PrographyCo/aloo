//
//  OrderDetailsTableViewCell.swift
//  Aloo
//
//  Created by Hany Alkahlout on 02/10/2021.
//

import UIKit

class OrderDetailsTableViewCell: UITableViewCell {
    static var identifier = "HomesTableViewCell"
    
    @IBOutlet weak var mealImageView: UIImageView!
    @IBOutlet weak var mealNameLabel: UILabel!
    @IBOutlet weak var withoutLabel: UILabel!
    @IBOutlet weak var mealNumberLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    
    
    
    func setData(data:Items){
        mealImageView.sd_setImage(with: URL(string: data.item?.img ?? ""))
        mealNameLabel.text = data.item?.name ?? ""
        withoutLabel.text =
            """
\(NSLocalizedString("with", comment: "")): \(data.data?.with ?? "")
\(NSLocalizedString("without", comment: "")): \(data.data?.without ?? "")
\(NSLocalizedString("drinks", comment: "")): \(data.data?.drinks?.first ?? "")
\(NSLocalizedString("extras", comment: "")): \(data.data?.extras?.first ?? "")
"""
        mealNumberLabel.text = "\(data.item?.amount ?? -1) \(data.item?.amount_type ?? "")"
        priceLabel.text = "\(String(data.item_price ?? -1.0)) \(NSLocalizedString("SR", comment: ""))"
        
    }
    
    func setCurrentOrdersItem(data:CurrentOrdersItems){
        mealImageView.sd_setImage(with: URL(string: data.item?.img ?? ""))
        mealNameLabel.text = data.item?.name ?? ""
        withoutLabel.text =
            """
\(NSLocalizedString("with", comment: "")): \(data.data?.with ?? "")
\(NSLocalizedString("without", comment: "")): \(data.data?.without ?? "")
\(NSLocalizedString("drinks", comment: "")): \(data.data?.drinks?.first?.name ?? "")
\(NSLocalizedString("extras", comment: "")): \(data.data?.extras?.first?.name ?? "")
"""
        mealNumberLabel.text = "\(data.item?.amount ?? -1) \(data.item?.amount_type ?? "")"
        priceLabel.text = "\(String(data.item_price ?? -1.0)) \(NSLocalizedString("SR", comment: ""))"
        
    }
    
}
