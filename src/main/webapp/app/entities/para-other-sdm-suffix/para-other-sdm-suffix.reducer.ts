import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaOtherSdmSuffix, defaultValue } from 'app/shared/model/para-other-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARAOTHERS: 'paraOther/SEARCH_PARAOTHERS',
  FETCH_PARAOTHER_LIST: 'paraOther/FETCH_PARAOTHER_LIST',
  FETCH_PARAOTHER: 'paraOther/FETCH_PARAOTHER',
  CREATE_PARAOTHER: 'paraOther/CREATE_PARAOTHER',
  UPDATE_PARAOTHER: 'paraOther/UPDATE_PARAOTHER',
  DELETE_PARAOTHER: 'paraOther/DELETE_PARAOTHER',
  SET_BLOB: 'paraOther/SET_BLOB',
  RESET: 'paraOther/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaOtherSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaOtherSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaOtherSdmSuffixState = initialState, action): ParaOtherSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAOTHERS):
    case REQUEST(ACTION_TYPES.FETCH_PARAOTHER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAOTHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAOTHER):
    case REQUEST(ACTION_TYPES.UPDATE_PARAOTHER):
    case REQUEST(ACTION_TYPES.DELETE_PARAOTHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAOTHERS):
    case FAILURE(ACTION_TYPES.FETCH_PARAOTHER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAOTHER):
    case FAILURE(ACTION_TYPES.CREATE_PARAOTHER):
    case FAILURE(ACTION_TYPES.UPDATE_PARAOTHER):
    case FAILURE(ACTION_TYPES.DELETE_PARAOTHER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAOTHERS):
    case SUCCESS(ACTION_TYPES.FETCH_PARAOTHER_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAOTHER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAOTHER):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAOTHER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAOTHER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-others';
const apiSearchUrl = 'api/_search/para-others';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaOtherSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAOTHERS,
  payload: axios.get<IParaOtherSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaOtherSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARAOTHER_LIST,
    payload: axios.get<IParaOtherSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaOtherSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAOTHER,
    payload: axios.get<IParaOtherSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaOtherSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAOTHER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaOtherSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAOTHER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaOtherSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAOTHER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
