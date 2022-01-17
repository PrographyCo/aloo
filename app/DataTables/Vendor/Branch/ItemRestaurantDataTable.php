<?php
namespace App\DataTables\Vendor\Branch;

use App\Http\Controllers\Controller;
use App\Models\Branch;
use App\Models\Item;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ItemRestaurantDataTable extends DataTable
{
    protected $branch;
    public function __construct($branch_id)
    {
        $this->branch = Branch::find($branch_id);
        Item::$show = true;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('brief_desc_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'brief_desc_'.Controller::$lang}, 30);
            })
            ->addColumn('action', function ($query){
                    return "<div class='form-check form-switch checkbox-input'><input type='checkbox' class='form-check-input item-input-amount' data-ajax='".route('vendor.branch.changeAmount', [$this->branch,  $query->item_id])."' data-id='$query->id' ".($query->amount == -1 ? "checked" : "")."></div>".
                        '<a href="'. route('vendor.item.show' ,$query->item_id) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                        '<a href="'. route('vendor.item.edit' ,$query->item_id) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';

            })->rawColumns([
                'action',
                'brief_desc_'.Controller::$lang,
            ]);
    }


    public function query()
    {
        return $this->branch->item()->with('category');
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('item-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    protected function getColumns()
    {
        return [
            Column::make('item_id'),
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.branch.item.name')),
            Column::make('category.name_'.Controller::$lang)->title(__('web/vendor/datatable.branch.item.desecration')),
            Column::make('optionals')->title(__('web/vendor/datatable.branch.item.optionals')),
            Column::make('extras[].name')->title(__('web/vendor/datatable.branch.item.extras'))->content('nothing'),
            Column::make('drinks[].name')->title(__('web/vendor/datatable.branch.item.drinks'))->content('nothing'),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    protected function filename()
    {
        return 'items_' . date('YmdHis');
    }
}
